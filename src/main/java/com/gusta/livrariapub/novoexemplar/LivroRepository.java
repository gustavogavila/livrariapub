package com.gusta.livrariapub.novoexemplar;

import com.gusta.livrariapub.novolivro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByIsbn(String isbn);
}
