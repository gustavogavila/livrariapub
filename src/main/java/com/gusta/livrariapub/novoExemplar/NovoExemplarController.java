package com.gusta.livrariapub.novoExemplar;

import com.gusta.livrariapub.novoLivro.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NovoExemplarController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EntityManager entityManager;

    @PostMapping("livro/{isbn}/exemplares")
    @Transactional
    public ResponseEntity<?> criar(@PathVariable("isbn") String isbn, @RequestBody @Valid NovoExemplarRequest novoExemplarRequest) {
        Optional<Livro> possivelLivro = livroRepository.findByIsbn(isbn);
        if (possivelLivro.isPresent()) {
            Exemplar exemplar = novoExemplarRequest.toModel(possivelLivro.get());
            entityManager.persist(exemplar);
            return ResponseEntity.ok(exemplar.getId());
        }
        return ResponseEntity.notFound().build();
    }
}
