package com.denteconvenio.userservice.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.denteconvenio.userservice.domain.user.User;
import com.denteconvenio.userservice.infra.security.LoginDTO;
import com.denteconvenio.userservice.infra.security.TokenDTO;
import com.denteconvenio.userservice.infra.security.TokenService;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    public TokenDTO efetuarLogin(LoginDTO dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());
        return new TokenDTO(tokenJWT);
    }
    
}
