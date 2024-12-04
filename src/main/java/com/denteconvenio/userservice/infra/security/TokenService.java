package com.denteconvenio.userservice.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.denteconvenio.userservice.domain.user.User;
import com.denteconvenio.userservice.domain.user.UserDTO;
import com.denteconvenio.userservice.infra.validation.ValidacaoException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(User usuario){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String authorities = usuario.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .collect(Collectors.joining(","));
            return JWT.create()
                    .withIssuer("denteconvenio")
                    .withExpiresAt(dataExpiracao())
                    .withClaim("roles", authorities)
                    .withClaim("tipo", usuario.getTipo().toString())
                    .withClaim("id", usuario.getId().toString())
                    .withClaim("email", usuario.getUsername())
                    .sign(algoritmo);

                } catch (JWTCreationException exception) {
                    throw new RuntimeException("Erro ao gerar Token", exception);
                } 
    }
            
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    

    // Decodifica token usando mesmo algoritmo de criaão
    public DecodedJWT decodificadorToken(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            var verify = JWT.require(algoritmo)
            .withIssuer("denteconvenio")
            .build()
            .verify(tokenJWT);

            return verify;
        } catch (JWTVerificationException exception) {
            throw new ValidacaoException("Token JWT invalido ou expirado!");
        }
    }

    // Recupera o token da requesição
    public String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null) {
                return authorizationHeader.replace("Bearer ", "");
            }
        return null;
    }

    // Extrai informações do token e passa para UserDTO
    public UserDTO extrairInformacoes(HttpServletRequest request) {
        var token = recuperarToken(request);
        DecodedJWT decodedJWT = decodificadorToken(token);
        UUID id = decodedJWT.getClaim("id").as(UUID.class);
        String email = decodedJWT.getClaim("email").asString();
        String tipo = decodedJWT.getClaim("tipo").asString();
        String username = decodedJWT.getSubject();
        UserDTO userDTO = new UserDTO(id, username, email, tipo);
        return userDTO;
    }

    // Extrai "ROLE" de token
    public GrantedAuthority getAuthorities(String tokenJWT) {
        DecodedJWT decodedJWT = decodificadorToken(tokenJWT);
        String role = decodedJWT.getClaim("roles").asString();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        System.out.println(authority);
        return authority;
    }

}