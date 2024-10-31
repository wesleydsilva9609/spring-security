package br.com.jwt.teste.teste.security.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record DadosUsuario(Long id,
                           @Email
                           String email,
                           @NotBlank
                           String password) {

}

