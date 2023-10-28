package com.example.exercicio.entities;

import com.example.exercicio.enumType.TipoProdutoType;
import jakarta.persistence.*;


@Entity
public class ProdutoTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;

    @Enumerated(EnumType.STRING)
    private TipoProdutoType tipoProdutoType;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public TipoProdutoType getTipoProdutoType() {
        return tipoProdutoType;
    }

    public void setTipoProdutoType(TipoProdutoType tipoProdutoType) {
        this.tipoProdutoType = tipoProdutoType;
    }
}
