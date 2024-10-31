package br.com.jwt.teste.teste.security.usuario;

public record UsuariosListados(Long id, String email) {

    public UsuariosListados(Usuario usuario){
        this(usuario.getId(), usuario.getEmail());
    }
}
