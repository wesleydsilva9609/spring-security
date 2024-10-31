package br.com.jwt.teste.teste.security.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizados(Long id,
                               String email,
                               String password) {
}
