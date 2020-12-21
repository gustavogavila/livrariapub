package com.gusta.livrariapub.novousuario;

import com.gusta.livrariapub.novoemprestimo.NovoEmprestimoRequest;
import com.gusta.livrariapub.novoemprestimo.PedidoEmprestimoComTempo;

public enum TipoUsuario {
    PADRAO {
        @Override
        boolean aceitaTempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
            return pedido.possuiPrazo();
        }
    }, PESQUISADOR {
        @Override
        boolean aceitaTempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
            return true;
        }
    };

    abstract boolean aceitaTempoEmprestimoValido(PedidoEmprestimoComTempo pedido);
}
