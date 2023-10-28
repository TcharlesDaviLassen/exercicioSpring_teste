package com.example.exercicio.service;

import com.example.exercicio.controller.TipoProduto;
import com.example.exercicio.entities.ProdutoTeste;

import java.util.List;

public interface TipoProdutoService {

    ProdutoTeste createTeste(ProdutoTeste entity);

    List<ProdutoTeste> findAllTeste();
}
