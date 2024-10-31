package br.com.jwt.teste.teste.security.controller;

import br.com.jwt.teste.teste.security.service.TokenService;
import br.com.jwt.teste.teste.security.usuario.DadosTokenJwt;
import br.com.jwt.teste.teste.security.usuario.Dadosjwt;
import br.com.jwt.teste.teste.security.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class ControllerLogin {
    @Autowired
   private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

      @PostMapping
     public ResponseEntity efetuarLogin(@RequestBody @Valid DadosTokenJwt dadosTokenJwt){
        var token = new UsernamePasswordAuthenticationToken(dadosTokenJwt.email(),dadosTokenJwt.password());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new Dadosjwt(tokenJWT));
    }
}
