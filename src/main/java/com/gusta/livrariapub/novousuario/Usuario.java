package com.gusta.livrariapub.novousuario;

import com.gusta.livrariapub.novoemprestimo.NovoEmprestimoRequest;
import com.gusta.livrariapub.novoemprestimo.PedidoEmprestimoComTempo;
import com.gusta.livrariapub.novolivro.Livro;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo_usuario")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Usuario() {}

    public Usuario(@NotNull TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        Assert.state(id != null, "Id não pode ser nulo. Você persistiu essa entidade?");
        return id;
    }

    public boolean isTipo(TipoUsuario tipoUsuario) {
        return this.tipo.equals(tipoUsuario);
    }

    public boolean tempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
        return tipo.aceitaTempoEmprestimoValido(pedido);
    }
}
