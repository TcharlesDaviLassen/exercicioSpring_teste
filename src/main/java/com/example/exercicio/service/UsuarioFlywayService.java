package com.example.exercicio.service;

import com.example.exercicio.entities.UsuarioFlyway;

import java.text.ParseException;
import java.util.List;

public interface UsuarioFlywayService {

    UsuarioFlyway salvarUsuarioFlyway(UsuarioFlyway usuarioFlyway) throws ParseException;

    List<UsuarioFlyway> findByAll();

}
