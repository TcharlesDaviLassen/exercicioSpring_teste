package com.example.exercicio.controller;

import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioFlywayServiceImpl usuarioFlywayService;

    public UsuarioController(UsuarioFlywayServiceImpl usuarioFlywayService) {
        this.usuarioFlywayService = usuarioFlywayService;
    }


    @GetMapping("/selecionarUsuario")
    public String selecionarUsuario(Model model, @RequestParam(name = "tipo", required = false) String tipo) {
        if (tipo != null) {
            try {
                UsuarioEnumType usuarioEnumType = UsuarioEnumType.valueOf(tipo.toUpperCase());

                List<UsuarioFlyway> usuariosPorTipo = usuarioFlywayService.findByEnum(usuarioEnumType);
                model.addAttribute("usuarios", usuariosPorTipo);
                model.addAttribute("tipo", tipo);


            } catch (IllegalArgumentException e) {
                // Trate o caso em que o tipo não é válido
                model.addAttribute("mensagemErro", "Tipo de usuário inválido");
            }
        }

        model.addAttribute("tiposUsuario", UsuarioEnumType.values());

        return "usuarioPage";
    }


//    @GetMapping("/usuarios")
//    public String mostrarPagina(Model model) {
//        // Carregue os dados dos usuários e adicione-os ao modelo
//        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();
//        model.addAttribute("usuarios", usuarios);
//        return "pagina_usuarios";
//    }

    @GetMapping("/usuarioPage")
    public String getUsuarioPage(Model model, UsuarioFlyway usuarioFlyway) {
        Integer[] leng = new Integer[5];
        List<UsuarioFlyway> findAllUsuarios = usuarioFlywayService.findByAll();

        model.addAttribute("usuarioFlyway", findAllUsuarios);
        model.addAttribute("usuarioFlywayTh", leng);
        model.addAttribute("usuarioCreate", new UsuarioFlyway());

        return "usuarioPage";
    }

    @RequestMapping(value = "/filtrar",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody List<UsuarioFlyway> filtrarUsuarios(@RequestBody UsuarioFlyway filtro) {

        if (filtro.getUsuarioEnumTypeEnum() != null) {
            return usuarioFlywayService.findByEnum(filtro.getUsuarioEnumTypeEnum());
        } else {
                        return usuarioFlywayService.findByAll();
//            return usuarioFlywayService.filtrarUsuarios(filtro);
        }
        //        // Implemente a lógica de filtragem com base nos critérios do filtro
        //        try {
        //            UsuarioEnumType tipo = UsuarioEnumType.valueOf(filtro.getUsuarioEnumTypeEnum().name());
        //
        //            // Se o valor do enum for válido, você pode prosseguir com o processamento
        //            // Aqui você pode fazer o que for necessário com o usuário e o tipo
        //
        ////            return ResponseEntity.ok("Usuário cadastrado: Nome = " + filtro.getNome() + ", Tipo = " + tipo.name());
        //        } catch (IllegalArgumentException e) {
        //            // Se o valor do enum for inválido, retorne uma resposta de erro
        ////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valor de tipo inválido: " + usuario.getTipo());
        //        }

        //        return usuarioFlywayService.filtrarUsuarios(filtro);
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

        Optional<UsuarioFlyway> editUsuarios = usuarioFlywayService.findById(id);

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
        var editUser =  usuarioFlywayService.edit(usuarioFlyway);
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
        usuarioFlywayService.create(usuarioFlyway);

        return "redirect:/usuario/usuarioPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteGetRegistro(@PathVariable(name = "id") Long id) {
        usuarioFlywayService.deleteUser(id);

        return "redirect:/usuario/usuarioPage";
    }
}
