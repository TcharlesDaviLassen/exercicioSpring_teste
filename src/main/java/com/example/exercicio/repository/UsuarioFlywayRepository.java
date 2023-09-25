package com.example.exercicio.repository;

import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioFlywayRepository extends JpaRepository<UsuarioFlyway, Long> {

    UsuarioFlyway findByNomeAndEmail(String nome, String email);

    List<UsuarioFlyway> findByEnum(UsuarioEnumType funcionarioSetor);

}
