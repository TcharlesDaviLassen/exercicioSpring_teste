package com.example.exercicio.DTO;

import com.example.exercicio.enumType.UsuarioEnumType;

public class UsuarioDTO {
    private String nome;
    private String numero;
    private String email;
    private String data;
    private UsuarioEnumType usuarioEnumTypeEnum;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nome, String numero, String email, String data, UsuarioEnumType usuarioEnumTypeEnum) {
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.data = data;
        this.usuarioEnumTypeEnum = usuarioEnumTypeEnum;
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


