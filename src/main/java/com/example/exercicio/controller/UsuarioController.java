package com.example.exercicio.controller;


import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.service.UsuarioFlywayService;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioFlywayServiceImpl serviceUsuarioFlyway;

    public UsuarioController(UsuarioFlywayServiceImpl serviceUsuarioFlyway) {
        this.serviceUsuarioFlyway = serviceUsuarioFlyway;
    }

    @GetMapping("/usuarioPage")
    public String getUsuarioPage(Model model, UsuarioFlyway usuarioFlyway) {
        List<UsuarioFlyway> findAllUsuarios = serviceUsuarioFlyway.findByAll();

        model.addAttribute("usuarioFlyway", findAllUsuarios);

        return "usuarioPage";
    }

//    produces = "application/json"
    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<UsuarioFlyway> create(@Valid @RequestBody UsuarioFlyway usuarioFlyway) {
        UsuarioFlyway usuarioCreate = serviceUsuarioFlyway.salvarUsuarioFlyway(usuarioFlyway);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreate);
    }

}
