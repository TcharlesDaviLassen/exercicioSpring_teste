package com.example.exercicio.service;

import java.util.List;

import com.example.exercicio.entities.Boleto;

public interface BoletoService {

    Boleto criarBoleto(Boleto boleto);

    List<Boleto> listarBoletos();

}
