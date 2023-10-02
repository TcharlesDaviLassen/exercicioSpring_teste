package com.example.exercicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exercicio.entities.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long>{
    
}
