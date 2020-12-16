package com.gusta.livrariapub.novoExemplar;

import com.gusta.livrariapub.novoLivro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByIsbn(String isbn);
}