package com.example.jacksonConfig;

import com.example.exercicio.enumType.UsuarioEnumType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class UsuarioEnumTypeDeserializer extends JsonDeserializer<UsuarioEnumType> {
    @Override
    public UsuarioEnumType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String tipoString = node.asText();

        // Mapeie os valores do JSON para os valores do enum aqui
        if ("E".equalsIgnoreCase(tipoString)) {
            return UsuarioEnumType.EMAIL;
        } else if ("N".equalsIgnoreCase(tipoString)) {
            return UsuarioEnumType.NOME;
        }

        throw new IllegalArgumentException("Valor de tipo inválido: " + tipoString);
    }
}
