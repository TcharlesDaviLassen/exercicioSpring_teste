package com.example.exercicio.enumType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

//@JsonDeserialize(using = UsuarioEnumTypeDeserializer.class)
public enum UsuarioEnumType implements RequireTypes {

     NOME("N", "NOME"),
     EMAIL("E", "EMAIL");

//    N("N", "NOME"),
//    E("E", "EMAIL");

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
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static UsuarioEnumType fromId(String id) {
        for (UsuarioEnumType tipo : UsuarioEnumType.values()) {
            if (tipo.id.equals(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor inválido para UsuarioEnumType: " + id);
    }

    // public UsuarioEnumType parseJsonToEnum(String json) {
    // ObjectMapper objectMapper = new ObjectMapper();
    // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
    // false);
    //
    // try {
    // return objectMapper.readValue(json, UsuarioEnumType.class);
    // } catch (IOException e) {
    // // Trate a exceção aqui e defina um valor padrão, se necessário
    // return UsuarioEnumType.N; // Substitua pelo valor padrão desejado
    // }
    // }

    @Override
    public String toString() {
        return description; // Retorna a descrição ao chamar toString()
    }
}
