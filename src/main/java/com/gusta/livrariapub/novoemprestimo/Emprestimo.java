package com.gusta.livrariapub.novoemprestimo;

import com.gusta.livrariapub.novoexemplar.Exemplar;
import com.gusta.livrariapub.novousuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToOne
    private Exemplar exemplar;

    private Integer diasEmprestimo;

    public Emprestimo(@NotNull @Positive Long usuarioId, @NotNull @Positive Long livroId, @Positive Integer diasEmprestimo) {

    }

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Emprestimo() {}
}
