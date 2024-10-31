package br.com.jwt.teste.teste.security.infra;

import br.com.jwt.teste.teste.security.service.TokenService;
import br.com.jwt.teste.teste.security.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecutiryFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenjwt = recuperarToken(request);

        if(tokenjwt != null){
            var subject = tokenService.getSubject(tokenjwt);
            var usuario = repository.findByEmail(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);


        }


        filterChain.doFilter(request,response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null){
            return  authenticationHeader.replace("Bearer","").trim();
        }
        return null;
    }
}
