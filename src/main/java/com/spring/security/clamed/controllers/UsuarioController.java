package com.spring.security.clamed.controllers;

import com.spring.security.clamed.dto.UsuarioInput;
import com.spring.security.clamed.model.Usuario;
import com.spring.security.clamed.repository.UsuarioRepository;
import com.spring.security.clamed.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioInput usuarioInput){

        Usuario usuario = toModel(usuarioInput);
        return new ResponseEntity<Usuario>(usuarioService.salvar(usuario), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
        return new ResponseEntity<Usuario>(usuarioService.salvar(usuario), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long idUsuario){

        usuarioService.delete(idUsuario);
        return new ResponseEntity<String>("Usuário deletado com sucesso.",HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idUsuario}")
    public ResponseEntity<String> deletePathVariable(@PathVariable(value = "idUsuario") Long idUsuario){

        usuarioService.delete(idUsuario);
        return new ResponseEntity<String>("Usuário deletado com sucesso.",HttpStatus.OK);
    }


    @GetMapping(value = "/")
    public ResponseEntity<List<Usuario>> getUsersByName(@RequestParam (name = "nome") String nome){

        // obtem a lista de usuários model
        List<Usuario> usuarios = usuarioService.findUsersByName(nome);

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios(){

        return new ResponseEntity<List<Usuario>>(usuarioService.getUsers(), HttpStatus.OK);
    }

    // método para fazer a convesão do DTO de entrada (UsuarioInput) para Model (usuario)
    private Usuario toModel(UsuarioInput usuarioInput){
        Usuario usuario = new Usuario();

        BeanUtils.copyProperties(usuarioInput, usuario);

        return usuario;
    }

    // método para fazer a conversão de DTO de entrada

}
