package com.example.exercicio.enumType;

import org.springframework.stereotype.Component;

public enum EnumTypeTeste {
    TESTE("1","TESTE"),
    TESTE2("2", "TESTE2"),
    TESTE3("3", "TESTE3");


    private String id;
    private String descricao;

    EnumTypeTeste() {
    }

    EnumTypeTeste(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
