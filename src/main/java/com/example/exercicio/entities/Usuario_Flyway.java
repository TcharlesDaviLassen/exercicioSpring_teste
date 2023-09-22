package com.example.exercicio.entities;

import com.example.exercicio.Anotations.TableAlias;
import com.example.exercicio.classUtils.AbstractEntity;
import com.example.exercicio.classUtils.EntityInterface;
import jakarta.persistence.*;
import org.springframework.format.datetime.DateFormatter;

import java.io.Serial;
import java.io.Serializable;
import java.util.TimeZone;

@Entity
//@TableAlias("pess")
public class Usuario_Flyway extends AbstractEntity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String numero;
    private String email;
    private TimeZone data;

    public Usuario_Flyway() {
    }

    public Usuario_Flyway(Long id, String nome, String numero, String email, TimeZone data) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.data = data;
    }

    @Override
    public Long getId() {
        return super.id;
    }

    @Override
    public void setId(Long id) {
        super.id = id;
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

    public TimeZone getData() {
        return data;
    }

    public void setData(TimeZone data) {
        this.data = data;
    }
}
