package com.example.exercicio.enumType;

public enum UsuarioEnumType implements RequireTypes {

    NOME("N", "NOME"),
    EMAIL("E", "EMAIL");

    private String id;
    private String description;

    UsuarioEnumType() {
    }

    UsuarioEnumType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
