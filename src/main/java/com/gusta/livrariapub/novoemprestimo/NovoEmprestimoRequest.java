package com.gusta.livrariapub.novoemprestimo;

import com.gusta.livrariapub.novolivro.Livro;
import com.gusta.livrariapub.novousuario.Usuario;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.Optional;

public class NovoEmprestimoRequest implements PedidoEmprestimoComTempo {
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
        return Optional.ofNullable(diasEmprestimo).isPresent();
    }

    public Emprestimo toModel(EntityManager entityManager) {
        Livro livro = entityManager.find(Livro.class, livroId);
        Usuario usuario = entityManager.find(Usuario.class, usuarioId);

        Assert.state(Objects.nonNull(livro), "O livro precisa existir nesse ponto do código.");
        Assert.state(Objects.nonNull(usuario), "O usuário precisa existir nesse ponto do código.");
        Assert.state(usuario.tempoEmprestimoValido(this), "Olha, você está tentando criar um empréstimo com um tempo não liberado para este usuário.");

        int limiteMaximoTempoEmprestimo = 60;
        int tempo = Objects.nonNull(diasEmprestimo) ? diasEmprestimo.intValue() : limiteMaximoTempoEmprestimo;

        return livro.criarEmprestimo(usuario, tempo);
    }
}
