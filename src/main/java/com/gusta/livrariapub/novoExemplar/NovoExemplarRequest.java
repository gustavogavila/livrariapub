package com.gusta.livrariapub.novoExemplar;

import com.gusta.livrariapub.novoLivro.Livro;

import javax.validation.constraints.NotNull;

public class NovoExemplarRequest {

    @NotNull
    private TipoCirculacao tipoCirculacao;

    public void setTipoCirculacao(TipoCirculacao tipoCirculacao) {
        this.tipoCirculacao = tipoCirculacao;
    }

    public Exemplar toModel(Livro livro) {
        return new Exemplar(livro, tipoCirculacao);
    }
}
