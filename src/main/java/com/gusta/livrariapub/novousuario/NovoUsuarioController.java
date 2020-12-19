package com.gusta.livrariapub.novousuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class NovoUsuarioController {

    @Autowired
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<Long> criar(@RequestBody @Valid NovoUsuarioRequest novoUsuarioRequest) {
        Usuario novoUsuario = novoUsuarioRequest.toModel();
        entityManager.persist(novoUsuario);
        return ResponseEntity.ok(novoUsuario.getId());
    }
}
