package br.com.jwt.teste.teste.security.controller;

import br.com.jwt.teste.teste.security.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosUsuario dados, UriComponentsBuilder uriComponentsBuilder){
        var user = new Usuario(dados);
        repository.save(user);
        var uri = uriComponentsBuilder.path("/login{id}").buildAndExpand(dados.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<UsuariosListados>>listar(@PageableDefault(size = 10)Pageable pageable){
        var user = repository.findAll(pageable).map(UsuariosListados::new);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity listar(@RequestBody @PathVariable Long id){
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new UsuariosListados(user));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizados dadosAtualizados){
        var use = repository.getReferenceById(dadosAtualizados.id());
        use.Atualiza(dadosAtualizados);
        return ResponseEntity.ok(new DetalhamentoUsuarios(use));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var user = repository.getReferenceById(id);
        user.excluir();
        return  ResponseEntity.noContent().build();
    }


}
