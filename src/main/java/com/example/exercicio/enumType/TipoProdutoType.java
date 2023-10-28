package com.example.exercicio.enumType;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoProdutoType implements RequireTypes{
    ELETRONICO("E","ELETRONICO"),
    VESTUARIO("V", "VESTUARIO"),
    ALIMENTO("A","ALIMENTO"),
    OUTRO("O", "OUTRO"),;

    TipoProdutoType() {
    }

    TipoProdutoType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    private String id;
    private String description;


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

