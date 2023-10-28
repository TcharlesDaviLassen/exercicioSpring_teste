package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.controller.TipoProduto;
import com.example.exercicio.entities.ProdutoTeste;
import com.example.exercicio.repository.TipoProdutoRepository;
import com.example.exercicio.service.TipoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProdutoServiceImpl implements TipoProdutoService {


    private final TipoProdutoRepository tipoProdutoRepository;
    @Autowired
    public TipoProdutoServiceImpl(TipoProdutoRepository tipoProdutoRepository) {
        this.tipoProdutoRepository = tipoProdutoRepository;
    }

    @Override
    public ProdutoTeste createTeste(ProdutoTeste entity) {
        return tipoProdutoRepository.save(entity);
    }

    @Override
    public List<ProdutoTeste> findAllTeste() {
        return tipoProdutoRepository.findAll();
    }
}
