package com.gusta.livrariapub.novoexemplar;

import com.gusta.livrariapub.novousuario.TipoUsuario;
import com.gusta.livrariapub.novousuario.Usuario;

public enum TipoCirculacao {
    LIVRE {
        @Override
        boolean aceita(Usuario usuario) {
            return true;
        }
    }, RESTRITO {
        @Override
        boolean aceita(Usuario usuario) {
            return usuario.isTipo(TipoUsuario.PESQUISADOR);
        }
    };

    abstract boolean aceita(Usuario usuario);
}
