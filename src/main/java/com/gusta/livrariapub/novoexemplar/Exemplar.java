package com.gusta.livrariapub.novoexemplar;

import com.gusta.livrariapub.novolivro.Livro;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id", referencedColumnName = "id")
    private @NotNull @Valid Livro livro;

    @Enumerated(EnumType.STRING)
    private @NotNull TipoCirculacao tipoCirculacao;

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Exemplar() {
    }

    public Exemplar(@NotNull Livro livro, @NotNull @Valid TipoCirculacao tipoCirculacao) {
        this.livro = livro;
        this.tipoCirculacao = tipoCirculacao;
    }

    public Long getId() {
        Assert.state(id != null, "O id está nulo. Persistiu esse dado?");
        return id;
    }

    public static boolean isLivreCirculacao(Exemplar exemplar) {
        return exemplar.tipoCirculacao.equals(TipoCirculacao.LIVRE);
    }
}
