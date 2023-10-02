package com.example.exercicio.entities;

public class DadosFiscais {
    private String numero;
    private double valor;

    public DadosFiscais() {
        // Construtor vazio necessário para deserialização JSON
    }

    public DadosFiscais(String numero, double valor) {
        this.numero = numero;
        this.valor = valor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
