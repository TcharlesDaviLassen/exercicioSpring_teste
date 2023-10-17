package com.example.exercicio.controller;

import com.example.exercicio.DTO.User;
import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.*;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.errorsUtils.BusinessException.BusinessException;
import com.example.exercicio.repository.OrderRepository;
import com.example.exercicio.service.serviceImpl.AddressService;
import com.example.exercicio.service.serviceImpl.MultiTransactionExampleService;
import com.example.exercicio.service.serviceImpl.OrderService;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/usuarioPage")
    public String getUsuarioPage(Model model) {
        List<UsuarioFlyway> findAllUsuarios = usuarioFlywayService.findByAll();

        model.addAttribute("usuarioFlyway", findAllUsuarios);
        model.addAttribute("noneRowDispayNone", findAllUsuarios);
        model.addAttribute("usuarioCreate", new UsuarioFlyway());

        return "usuarioPage";
    }

    @GetMapping("/inputs")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "inputsExample";
    }

    @PostMapping("/showForm")
    public String processForm(User user) {
        // Aqui você pode processar os dados do usuário (por exemplo, salvar no banco de
        // dados)
        return "redirect:/form";
    }

    @RequestMapping(value = "/filtrar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UsuarioFlyway> filtrarUsuarios(@RequestBody UsuarioFlyway filtro) {
        return usuarioFlywayService.findByAll();
    }

    // produces = "application/json"
    // @RequestMapping(value = "/create",
    // method = RequestMethod.POST,
    // consumes = MediaType.APPLICATION_JSON_VALUE
    // )
    // @ResponseBody
    // public ResponseEntity<UsuarioFlyway> create(@Valid @RequestBody UsuarioFlyway
    // usuarioFlyway) {
    // UsuarioFlyway usuarioCreate =
    // serviceUsuarioFlyway.salvarUsuarioFlyway(usuarioFlyway);
    //
    // return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreate);
    // }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable(name = "id") Long id, Model model) {
        Optional<UsuarioFlyway> editUsuarios = usuarioFlywayService.findById(id);

        if (editUsuarios.isEmpty()) {
            return "pageNotFound";
        } else {
            model.addAttribute("editUsuarios", editUsuarios.get());
            return "usuarioEditPage";
        }
    }

    // @RequestMapping(value = "/edit/{id}",
    // method = RequestMethod.POST,
    // consumes = MediaType.APPLICATION_JSON_VALUE
    // )
    // @ResponseBody
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, @Valid UsuarioFlyway usuarioFlyway) {
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








    // Teste transaction
    @Autowired
    private MultiTransactionExampleService multiTransactionExampleService;

    @GetMapping("/getTansaction")
    public void executeTransactions() {
        multiTransactionExampleService.performSuccessfulTransaction();
        multiTransactionExampleService.performFailedTransaction();
    }

    // Teste EmbeddedId
    @Autowired
    private OrderService orderService;

    @GetMapping("/executeOrderIdEmbeddedId")
    public void executeOrderIdEmbeddedId() {

        OrderId orderId = new OrderId("12345", 1L); // Número do pedido e ID do cliente
        Order order = new Order(orderId, "Product ABC", 5);
        orderService.createOrder(order);

        Order retrievedOrder = orderService.getOrder(orderId);
        System.out.println(retrievedOrder);
    }

    // Teste Embedded
    @Autowired
    private AddressService addressService;

    @GetMapping("/executeOrderIdEmbedded")
    private  void executeOrderIdEmbedded() {
        Address address = new Address();
        StreetInfo streetInfo = new StreetInfo("123", "Main Street");
        address.setStreetInfo(streetInfo);
        address.setCity("Sample City");
        address.setState("Sample State");

        addressService.createAddress(address);
    }



    @GetMapping("/some-api-endpoint")
    public String somePage(Model model) {
        try {
            //  Coloque aqui o código que pode lançar BusinessException
            String arg1 = "34534637";
            String arg2 = "98667856425435";
            // String errorMsg = String.format("Erro no mapa de totais. Argumento 1: %s, Argumento 2: %s", arg1, arg2);;
            throw new BusinessException(true, "error.mapa.totais", arg1, arg2);
        } catch (BusinessException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "error";
        }

    }

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
}
