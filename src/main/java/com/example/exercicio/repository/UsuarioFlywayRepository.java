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

    List<UsuarioFlyway> findByNome(String nome);

    List<UsuarioFlyway> findByNumero(String numero);

    List<UsuarioFlyway> findByEmail(String email);

    List<UsuarioFlyway> findByData(String data);

    List<UsuarioFlyway> findByNomeAndEmail(String nome, String email);

//    @Query(value = "SELECT * FROM usuario_flyway WHERE usuarioEnumTypeEnum = :tipo", nativeQuery = true)
//    List<UsuarioFlyway> findByUsuarioEnum(@Param("tipo") UsuarioEnumType funcionarioSetor);
    List<UsuarioFlyway> findByUsuarioEnum(UsuarioEnumType usuarioEnumType);

}
