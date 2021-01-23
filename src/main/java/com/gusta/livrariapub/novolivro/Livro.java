package com.gusta.livrariapub.novolivro;

import com.gusta.livrariapub.novoemprestimo.Emprestimo;
import com.gusta.livrariapub.novoexemplar.Exemplar;
import com.gusta.livrariapub.novoexemplar.LivroRepository;
import com.gusta.livrariapub.novoexemplar.TipoCirculacao;
import com.gusta.livrariapub.novousuario.TipoUsuario;
import com.gusta.livrariapub.novousuario.Usuario;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NotBlank String titulo;
    private @NotNull @Positive BigDecimal preco;
    private @NotBlank @ISBN(type = ISBN.Type.ISBN_10) String isbn;
    @OneToMany(mappedBy = "livro", fetch = FetchType.EAGER)
    // 1 ponto
    private List<Exemplar> exemplares = new ArrayList<>();

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Livro() {}

    public Livro(@NotBlank String titulo, @NotNull @Positive BigDecimal preco, @NotBlank @ISBN(type = ISBN.Type.ISBN_10) String isbn) {
        this.titulo = titulo;
        this.preco = preco;
        this.isbn = isbn;
    }

    public Long getId() {
        Assert.state(id != null, "Não rola chamar o getId do livro com id nulo");
        return id;
    }

    // 1 ponto
    public boolean aceitaSerEmprestado(Usuario usuario) {
        // 1 ponto
        return exemplares.stream().anyMatch(exemplar -> exemplar.aceita(usuario));
    }

    public Emprestimo criarEmprestimo(@NotNull @Valid Usuario usuario, @Positive int diasEmprestimo) {
        Assert.isTrue(this.aceitaSerEmprestado(usuario), "Você está gerando um emprestimo para um tipo de usuário não autorizado");

        // 1 ponto
        Exemplar exemplarSelecionado = exemplares.stream().filter(exemplar -> exemplar.aceita(usuario)).findFirst().get();

        // FIXME Quando se tem mais de 1 exemplar de livro, essa verificação sempre retorna falso, lançando um erro 500. Necessário verificar
        Assert.isTrue(exemplarSelecionado.disponivelParaEmprestimo(),
                "Olha, o seu código não deveria criar um empréstimo para um exemplar que não está disponível.");

        Emprestimo emprestimo = new Emprestimo(usuario, exemplarSelecionado, diasEmprestimo);
        exemplarSelecionado.adiciona(emprestimo);
        // 1 ponto
        return emprestimo;
    }

    public boolean estaDisponivelParaEmprestimo() {
        // Possíveis implementações:
        // guardar o instante de devolução no próprio empréstimo
        // criar uma abstração para representar a devolução
        return exemplares.stream().anyMatch(exemplar -> exemplar.disponivelParaEmprestimo());
    }
}
