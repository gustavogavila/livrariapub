package com.gusta.livrariapub.novoexemplar;

import com.gusta.livrariapub.novoemprestimo.Emprestimo;
import com.gusta.livrariapub.novolivro.Livro;
import com.gusta.livrariapub.novousuario.Usuario;
import com.zaxxer.hikari.util.FastList;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id", referencedColumnName = "id")
    private @NotNull @Valid Livro livro;

    @Enumerated(EnumType.STRING)
    private @NotNull TipoCirculacao tipoCirculacao;

    @OneToMany(mappedBy = "exemplarSelecionado")
    @OrderBy("instante_emprestimo asc")
    private SortedSet<Emprestimo> emprestimos = new TreeSet<>();

    /**
     * @deprecated (utilizado apenas pela JPA)
     */
    @Deprecated
    public Exemplar() {
    }

    public Exemplar(@NotNull Livro livro, @NotNull @Valid TipoCirculacao tipoCirculacao) {
        this.livro = livro;
        this.tipoCirculacao = tipoCirculacao;
    }

    public Long getId() {
        Assert.state(id != null, "O id está nulo. Persistiu esse dado?");
        return id;
    }

    public boolean aceita(Usuario usuario) {
        return this.tipoCirculacao.aceita(usuario);
    }

    public boolean disponivelParaEmprestimo() {
        // FIXME Quando se tem mais de 1 exemplar de livro, esse método sempre retorna falso, lançando um erro 500. Necessário verificar
        System.out.println(emprestimos);
        return emprestimos.isEmpty() || emprestimos.stream().allMatch(emprestimo -> emprestimo.foiDevolvido());
    }

    public void adiciona(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }
}
