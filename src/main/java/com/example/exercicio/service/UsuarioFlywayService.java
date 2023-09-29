package com.example.exercicio.service;

import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UsuarioFlywayService {

    UsuarioFlyway salvarUsuarioFlyway(UsuarioFlyway usuarioFlyway) throws ParseException;

    Optional<UsuarioFlyway> findById(Long id);

    List<UsuarioFlyway> findByNome(String nome);

    List<UsuarioFlyway> findByNumero(String numero);

    List<UsuarioFlyway> findByEmail(String email);

    List<UsuarioFlyway> findByData(String data);

    List<UsuarioFlyway> findByNomeAndEmail(String nome, String email);

    List<UsuarioFlyway> findByAll();

    List<UsuarioFlyway> findByEnum(UsuarioEnumType usuarioFlyway);

    UsuarioFlyway create(UsuarioFlyway usuarioFlyway);

    UsuarioFlyway edit(UsuarioFlyway id);

    void deleteUser(Long id);



}
