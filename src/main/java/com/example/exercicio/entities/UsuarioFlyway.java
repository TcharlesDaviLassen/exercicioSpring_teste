package com.example.exercicio.entities;

import com.example.exercicio.enumType.UsuarioEnumType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.TimeZone;

@Entity
//@TableAlias(value = "pess")
public class UsuarioFlyway implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String numero;
    private String email;
    private String data;

    @Column(name = "usuarioEnumtypeEnum", columnDefinition = "string")
    @Enumerated(value = EnumType.STRING)
    private UsuarioEnumType usuarioEnumTypeEnum;

    public UsuarioFlyway() {
    }


    public UsuarioFlyway(Long id, String nome, String numero, String email, String data, UsuarioEnumType usuarioEnumTypeEnum) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.data = data;
        this.usuarioEnumTypeEnum = usuarioEnumTypeEnum;
    }

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    @Temporal(TemporalType.TIMESTAMP)
    //    @JsonSerialize(using = TimeZoneSerializer.class)
    //    @JsonDeserialize(using = DateDeserializers.TimestampDeserializer.class)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UsuarioEnumType getUsuarioEnumTypeEnum() {
        return usuarioEnumTypeEnum;
    }

    public void setUsuarioEnumTypeEnum(UsuarioEnumType usuarioEnumTypeEnum) {
        this.usuarioEnumTypeEnum = usuarioEnumTypeEnum;
    }
}
