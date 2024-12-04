package com.denteconvenio.userservice.infra.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
        
        //Executa antes de qualquer ação para todos os metodos HTTP que foram "protegidos"        
        String token = tokenService.recuperarToken(request);
        if (token != null) {
            GrantedAuthority authority = tokenService.getAuthorities(token);
            
            // Cria o objeto de autenticação e configura o contexto de segurança
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

   
}