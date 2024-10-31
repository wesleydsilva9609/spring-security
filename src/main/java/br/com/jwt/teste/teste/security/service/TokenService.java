package br.com.jwt.teste.teste.security.service;

import br.com.jwt.teste.teste.security.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.spring.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("revisao")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(Dataexpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
           throw  new RuntimeException("erro ao gerar token",exception);
        }

    }
    public String getSubject(String tokenJWT){

        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("revisao")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
          throw new  RuntimeException("error na verificacao de token",exception);
        }
    }

    private Instant Dataexpiracao() {// revisar local date
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
