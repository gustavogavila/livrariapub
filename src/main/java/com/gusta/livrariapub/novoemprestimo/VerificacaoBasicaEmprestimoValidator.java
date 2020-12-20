package com.gusta.livrariapub.novoemprestimo;

import com.gusta.livrariapub.novoexemplar.TipoCirculacao;
import com.gusta.livrariapub.novolivro.Livro;
import com.gusta.livrariapub.novousuario.TipoUsuario;
import com.gusta.livrariapub.novousuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

@Component
public class VerificacaoBasicaEmprestimoValidator implements Validator {

    private EntityManager entityManager;

    @Autowired
    public VerificacaoBasicaEmprestimoValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NovoEmprestimoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NovoEmprestimoRequest request = (NovoEmprestimoRequest) o;
        Usuario usuario = entityManager.find(Usuario.class, request.getUsuarioId());
        Livro livro = entityManager.find(Livro.class, request.getLivroId());

        Assert.state(usuario != null, "O usuário deve existir para fazer a validação.");
        Assert.state(livro != null, "O livro deve existir para fazer a validação.");

        boolean possuiPrazo = request.possuiPrazo();

        if (!livro.aceitaSerEmprestado(usuario)) {
            errors.reject(null,"Este livro não pode ser emprestado.");
        }

        if (!possuiPrazo && usuario.isTipo(TipoUsuario.PADRAO)) {
            errors.reject(null,"Para usuário padrão é necessário informar o prazo de devolução.");
        }

    }
}
