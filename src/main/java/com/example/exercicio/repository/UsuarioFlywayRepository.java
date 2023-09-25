package com.example.exercicio.repository;

import com.example.exercicio.entities.UsuarioFlyway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioFlywayRepository extends JpaRepository<UsuarioFlyway, Long> {

    UsuarioFlyway findByNomeAndEmail(String nome, String email);

}
