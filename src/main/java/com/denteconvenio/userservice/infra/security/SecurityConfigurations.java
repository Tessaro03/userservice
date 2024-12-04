package com.denteconvenio.userservice.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {


    @Autowired
    private SecurityFilter securityFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req -> {
            req.requestMatchers(new AntPathRequestMatcher("/beneficiario/cadastrar", HttpMethod.POST.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/beneficiario", HttpMethod.GET.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/consultorio/cadastrar", HttpMethod.POST.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/dentista/cadastrar", HttpMethod.POST.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/empresa/cadastrar", HttpMethod.POST.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.name())).permitAll();
            req.requestMatchers(new AntPathRequestMatcher("/auth", HttpMethod.GET.name())).permitAll();
            
            // Necessita de Role para acessar
            req.requestMatchers(new AntPathRequestMatcher("/beneficiario", HttpMethod.DELETE.name())).hasRole("BENEFICIARIO");
            req.requestMatchers(new AntPathRequestMatcher("/beneficiario/idEmpresa", HttpMethod.GET.name())).hasRole("BENEFICIARIO");


            req.requestMatchers(new AntPathRequestMatcher("/consultorio")).hasRole("CONSULTORIO");
            req.requestMatchers(new AntPathRequestMatcher("/consultorio/dentistas", HttpMethod.POST.name())).hasRole("CONSULTORIO");
            req.requestMatchers(new AntPathRequestMatcher("/consultorio/dentistas", HttpMethod.DELETE.name())).hasRole("CONSULTORIO");
            req.requestMatchers(new AntPathRequestMatcher("/consultorio/consultorios", HttpMethod.GET.name())).permitAll();


            req.requestMatchers(new AntPathRequestMatcher("/dentista/consultorio/{id}", HttpMethod.DELETE.name())).hasRole("DENTISTA");
            req.requestMatchers(new AntPathRequestMatcher("/dentista")).hasRole("DENTISTA");

            req.requestMatchers(new AntPathRequestMatcher("/empresa", HttpMethod.PATCH.name())).hasRole("EMPRESA");
            req.requestMatchers(new AntPathRequestMatcher("/empresa", HttpMethod.GET.name())).hasRole("EMPRESA");
            req.requestMatchers(new AntPathRequestMatcher("/empresa", HttpMethod.DELETE.name())).hasRole("EMPRESA");
            req.requestMatchers(new AntPathRequestMatcher("/empresa/colaboradores", HttpMethod.GET.name())).hasRole("EMPRESA");
            req.requestMatchers(new AntPathRequestMatcher("/empresa/colaboradores", HttpMethod.POST.name())).hasRole("EMPRESA");
            req.requestMatchers(new AntPathRequestMatcher("/empresa/colaboradores", HttpMethod.DELETE.name())).hasRole("EMPRESA");

            req.anyRequest().authenticated();
        })
        .addFilterBefore(securityFilter, SecurityContextPersistenceFilter.class)
        .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}