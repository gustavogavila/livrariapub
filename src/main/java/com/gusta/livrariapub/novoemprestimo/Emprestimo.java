package com.gusta.livrariapub.novoemprestimo;

import com.gusta.livrariapub.novoexemplar.Exemplar;
import com.gusta.livrariapub.novousuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "exemplar_id", referencedColumnName = "id")
    private Exemplar exemplarSelecionado;

    @Positive
    @Column(name = "dias_emprestimo")
    private int diasEmprestimo;

    public Emprestimo(@NotNull @Valid Usuario usuario, @NotNull @Valid Exemplar exemplarSelecionado, @Positive int diasEmprestimo) {
        Assert.isTrue(exemplarSelecionado.aceita(usuario),
                "Olha, você está construindo um emprestimo com um exemplar que nao aceita esse usuario. Será que você verificou corretamente antes?");
        this.usuario = usuario;
        this.exemplarSelecionado = exemplarSelecionado;
        this.diasEmprestimo = diasEmprestimo;
    }

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Emprestimo() {}

    public Long getId() {
        Assert.state(Objects.nonNull(id), "Será que vc esqueceu de persistir o empréstimo?");
        return id;
    }
}
