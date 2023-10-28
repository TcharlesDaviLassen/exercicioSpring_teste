package com.example.exercicio.repository;

import com.example.exercicio.controller.TipoProduto;
import com.example.exercicio.entities.ProdutoTeste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProdutoRepository extends JpaRepository<ProdutoTeste, Long> {

}
