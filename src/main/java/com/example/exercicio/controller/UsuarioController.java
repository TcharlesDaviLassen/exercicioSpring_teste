package com.example.exercicio.controller;

import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioFlywayServiceImpl serviceUsuarioFlyway;

    public UsuarioController(UsuarioFlywayServiceImpl serviceUsuarioFlyway) {
        this.serviceUsuarioFlyway = serviceUsuarioFlyway;
    }

    @GetMapping("/selecionarUsuario")
    public String selecionarDia(Model model, @RequestParam(name = "tipo", required = false) String tipo) {
        UsuarioEnumType usuarioEnumType = UsuarioEnumType.valueOf(tipo.toUpperCase());

        List<UsuarioFlyway> usuarioEnumTypeEnum = serviceUsuarioFlyway.findByEnum(usuarioEnumType);
        model.addAttribute("tiposUsuario", usuarioEnumTypeEnum);
        model.addAttribute("tipo", tipo);

        return "usuarioPage";
    }

    @GetMapping("/usuarioPage")
    public String getUsuarioPage(Model model, UsuarioFlyway usuarioFlyway) {
        List<UsuarioFlyway> findAllUsuarios = serviceUsuarioFlyway.findByAll();

        model.addAttribute("usuarioFlyway", findAllUsuarios);

        return "usuarioPage";
    }

//    produces = "application/json"
//    @RequestMapping(value = "/create",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    @ResponseBody
//    public ResponseEntity<UsuarioFlyway> create(@Valid @RequestBody UsuarioFlyway usuarioFlyway) {
//        UsuarioFlyway usuarioCreate = serviceUsuarioFlyway.salvarUsuarioFlyway(usuarioFlyway);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreate);
//    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable(name = "id") Long id, Model model) {

        Optional<UsuarioFlyway> editUsuarios = serviceUsuarioFlyway.findById(id);

        if(editUsuarios.isEmpty()) {
            return "pageNotFound";
        } else {
            model.addAttribute("editUsuarios", editUsuarios.get());
            return "usuarioEditPage";
        }
    }

//    @RequestMapping(value = "/edit/{id}",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    @ResponseBody
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable( name = "id") Long id, @Valid UsuarioFlyway usuarioFlyway) {
        var editUser =  serviceUsuarioFlyway.edit(usuarioFlyway);
        //        return ResponseEntity.ok().body(editUser);

        return "redirect:/usuario/usuarioPage";
    }

    @GetMapping("/create")
    public String usuarioCreatePage(Model model) {

        model.addAttribute("usuarioCreate", new UsuarioFlyway());

        return "usuarioCreatePage";
    }

    @PostMapping("/create")
    public String criarRegistro(@ModelAttribute("usuarioCreate") UsuarioFlyway usuarioFlyway) {
        serviceUsuarioFlyway.create(usuarioFlyway);

        return "redirect:/usuario/usuarioPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteGetRegistro(@PathVariable(name = "id") Long id) {
        serviceUsuarioFlyway.deleteUser(id);

        return "redirect:/usuario/usuarioPage";
    }




}
