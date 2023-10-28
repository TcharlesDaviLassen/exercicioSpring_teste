package com.example.exercicio.configs;

import com.example.exercicio.entities.ProdutoTeste;
import com.example.exercicio.enumType.TipoProdutoType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(TipoProdutoType.class, new EnumSerializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
