package com.example.exercicio.configs;

import com.example.exercicio.entities.ProdutoTeste;
import com.example.exercicio.enumType.TipoProdutoType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class EnumSerializer extends StdSerializer<TipoProdutoType> {
    public EnumSerializer() {
        super(TipoProdutoType.class);
    }

    @Override
    public void serialize(TipoProdutoType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name());
    }
}
