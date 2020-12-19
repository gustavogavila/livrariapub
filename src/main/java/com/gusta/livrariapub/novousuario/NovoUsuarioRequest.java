package com.gusta.livrariapub.novousuario;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;

public class NovoUsuarioRequest {

    @NotNull
    private TipoUsuario tipo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovoUsuarioRequest(@NotNull TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Usuario toModel() {
        return new Usuario(tipo);
    }
}
