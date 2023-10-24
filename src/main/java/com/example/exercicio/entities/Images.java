package com.example.exercicio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @JsonProperty("destinationDirectory")
    private byte[] dados;

    private String nome;

    public Images() {
    }

    public Images(Long id, byte[] dados, String nome) {
        this.id = id;
        this.dados = dados;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
