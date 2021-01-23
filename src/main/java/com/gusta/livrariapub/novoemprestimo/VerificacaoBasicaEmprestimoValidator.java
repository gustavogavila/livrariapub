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

// 7
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
        // 1
        if (errors.hasErrors()) {
            return;
        }
        // 1
        NovoEmprestimoRequest request = (NovoEmprestimoRequest) o;
        // 1
        Usuario usuario = entityManager.find(Usuario.class, request.getUsuarioId());
        // 1
        Livro livro = entityManager.find(Livro.class, request.getLivroId());

        Assert.state(usuario != null, "O usuário deve existir para fazer a validação.");
        Assert.state(livro != null, "O livro deve existir para fazer a validação.");

        boolean possuiPrazo = request.possuiPrazo();
        // 1
        if (!livro.aceitaSerEmprestado(usuario)) {
            errors.reject(null,"Este livro não pode ser emprestado.");
        }

        // 1
        if (!usuario.tempoEmprestimoValido(request)) {
            errors.reject(null, "Você precisa definir o tempo do empréstimo.");
        }

        // 1
        // FIXME Quando o livro possui mais de um exemplar, a lista de empréstimos está sendo zerada e essa validação não é realizada.
        if (!livro.estaDisponivelParaEmprestimo()) {
            errors.reject(null, "Este livro não está disponível para empréstimo.");
        }
    }
}
