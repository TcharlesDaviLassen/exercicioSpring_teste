package com.example.exercicio.enumType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UsuarioEnumTypeDeserializer extends JsonDeserializer<UsuarioEnumType> {
    @Override
    public UsuarioEnumType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString().trim();

        for (UsuarioEnumType enumType : UsuarioEnumType.values()) {
            if (value.equalsIgnoreCase(enumType.getDescription()) || value.equalsIgnoreCase(enumType.name())) {
                return enumType;
            }
        }

        throw new IllegalArgumentException("Valor de tipo inválido: " + value);
    }
}
