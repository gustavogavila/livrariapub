package com.gusta.livrariapub.novolivro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "livros")
public class NovoLivroController {

    @Autowired
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public Long criar(@RequestBody @Valid NovoLivroRequest novoLivroRequest) {
        Livro novoLivro = novoLivroRequest.toModel();
        entityManager.persist(novoLivro);
        return novoLivro.getId();
    }

}
