package com.gusta.livrariapub.novoLivro;

import com.gusta.livrariapub.shared.UniqueValue;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovoLivroRequest {

    @NotBlank
    private String titulo;

    @NotNull
    @Positive
    private BigDecimal preco;

    @NotBlank
    @ISBN(type = ISBN.Type.ISBN_10)
    @UniqueValue(domainClass = Livro.class, fieldName = "isbn")
    private String isbn;

    public NovoLivroRequest(@NotBlank String titulo, @NotNull @Positive BigDecimal preco, @NotBlank @ISBN(type = ISBN.Type.ISBN_10) String isbn) {
        this.titulo = titulo;
        this.preco = preco;
        this.isbn = isbn;
    }

    public Livro toModel() {
        return new Livro(titulo, preco, isbn);
    }
}
