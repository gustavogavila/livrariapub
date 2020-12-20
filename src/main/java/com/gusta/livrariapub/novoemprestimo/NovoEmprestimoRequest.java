package com.gusta.livrariapub.novoemprestimo;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovoEmprestimoRequest {
    @NotNull
    @Positive
    private Long usuarioId;

    @NotNull
    @Positive
    private Long livroId;

    @Range(min = 1, max = 60)
    private Integer diasEmprestimo;

    public NovoEmprestimoRequest(@NotNull @Positive Long usuarioId, @NotNull @Positive Long livroId) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public void setDiasEmprestimo(Integer diasEmprestimo) {
        this.diasEmprestimo = diasEmprestimo;
    }

    public Long getUsuarioId() {
        Assert.state(usuarioId != null, "Você passsou o id do usuário?");
        return usuarioId;
    }

    public Long getLivroId() {
        Assert.state(livroId != null, "Você passou o id do livro?");
        return livroId;
    }

    public boolean possuiPrazo() {
        return this.diasEmprestimo != null;
    }
}
