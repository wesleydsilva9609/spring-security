package br.com.jwt.teste.teste.security.usuario;

public record DetalhamentoUsuarios(Long id, String email, String password) {
    public DetalhamentoUsuarios(Usuario use) {
        this(use.getId(), use.getEmail(), use.getPassword());
    }
}
