package com.gusta.livrariapub.novoemprestimo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("emprestimos")
public class NovoEmprestimoController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VerificacaoBasicaEmprestimoValidator verificacaoBasicaEmprestimoValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(verificacaoBasicaEmprestimoValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Long> criar(@RequestBody @Valid NovoEmprestimoRequest novoEmprestimoRequest) {
        Emprestimo novoEmprestimo = novoEmprestimoRequest.toModel(entityManager);
        entityManager.persist(novoEmprestimo);
        return ResponseEntity.ok(novoEmprestimo.getId());
    }

}
