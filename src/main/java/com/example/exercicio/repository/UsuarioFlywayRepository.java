package com.example.exercicio.repository;

import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioFlywayRepository extends JpaRepository<UsuarioFlyway, Long> {

    UsuarioFlyway findByNomeAndEmail(String nome, String email);

//    @Query(value = "SELECT * FROM usuario_flyway WHERE usuarioEnumTypeEnum = :tipo", nativeQuery = true)
//    List<UsuarioFlyway> findByUsuarioEnumTypeEnum(@Param("tipo") UsuarioEnumType funcionarioSetor);
    List<UsuarioFlyway> findByUsuarioEnumTypeEnum(UsuarioEnumType usuarioEnumType);

}
