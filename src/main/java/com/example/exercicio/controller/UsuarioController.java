package com.example.exercicio.controller;

import com.example.exercicio.DTO.User;
import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.*;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.errorsUtils.BusinessException.BusinessException;
import com.example.exercicio.errorsUtils.customRuntimeExempion.CustomException;
import com.example.exercicio.errorsUtils.customRuntimeExempion.ResourceFoundExceptionWithHttpStatus;
import com.example.exercicio.repository.OrderRepository;
import com.example.exercicio.service.serviceImpl.AddressService;
import com.example.exercicio.service.serviceImpl.MultiTransactionExampleService;
import com.example.exercicio.service.serviceImpl.OrderService;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;
import java.util.List;

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

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, @Valid UsuarioFlyway usuarioFlyway) {

        usuarioFlywayService.edit(usuarioFlyway);
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

    // Teste EmbeddedId, incorpora na chave ID da entidade chaves campostas adicionais.
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

    // Teste Embedded, incorpora no corpo da entidade valores campos campostos adicionais.
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
        String arg1 = "34534637";
        String arg2 = "98667856425435";
        String arg3 = "98667856425435";

        try {
            //  Coloque aqui o código que pode lançar BusinessException
            // String errorMsg = String.format("Erro no mapa de totais. Argumento 1: %s, Argumento 2: %s", arg1, arg2);;
            throw new BusinessException(true, "error.mapa.totais", arg1, arg2);
        } catch (BusinessException ex ) {
            model.addAttribute("errorMsg", ex.getMessage());

            var mess = new BusinessException(true, "error.mapa.totais.info", arg1, arg2, arg3);
            model.addAttribute("errorMsgInfo", mess.getMessage());

            return "error";
        }
    }

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }


    //    @RequestMapping(value = "/jasperPDF",
    //            consumes = MediaType.APPLICATION_JSON_VALUE,
    //            produces = MediaType.APPLICATION_JSON_VALUE,
    //            method = RequestMethod.POST
    //    )
    @RequestMapping(value = "/jasperPDF", method = RequestMethod.GET)
    public void jasperPDF(HttpServletResponse response) {
        try {
            //        // Obter os dados a serem incluídos no relatório
            //        var findByAllUsers = usuarioFlywayService.findByAll();
            //
            //        // Criar uma fonte de dados a partir dos dados dos usuários
            //        JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);
            //
            //        // Definir parâmetros que podem ser usados no relatório
            //        Map<String, Object> parameters = new HashMap<>();
            //        parameters.put("tituloRelatorio", "Relatório de Produtos");
            //        parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            //
            //        // Especificar o caminho do arquivo JRXML do relatório
            //        String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            //
            //        // Compilar o relatório a partir do arquivo JRXML
            //        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
            //
            //        // Preencher o relatório com dados
            //        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanDataSource);
            //
            //        // Configurar a resposta HTTP para um PDF
            //        response.setContentType("application/pdf");
            //        response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
            //
            //        // Exportar o relatório para um fluxo de saída
            //        OutputStream outStream = response.getOutputStream();
            //        JRPdfExporter exporter = new JRPdfExporter();
            //        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            //        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            //        exporter.exportReport();
            //
            //        // Exportar o relatório para um arquivo PDF no servidor
            //        String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            //        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            //        outStream.flush();
            //        outStream.close();


            //            var findByAllUsers = usuarioFlywayService.findByAll();
            //            //// Converter a lista de findByAllUsers em um JRBeanCollectionDataSource
            //            //// JRBeanCollectionDataSource: Esta é uma classe do JasperReports que permite criar uma fonte de dados a partir de uma coleção de objetos Java (no seu caso, objetos representando registros de usuários).
            //            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);
            //
            //            Map<String, Object> parameters = new HashMap<>();
            //            parameters.put("tituloRelatorio", "Relatório de Produtos");
            //            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            //
            //            //// para compilar o relatório JasperReports. O método compileReport é usado para compilar o arquivo JRXML no caminho especificado em reportTemplatePath. O resultado compilado é armazenado na variável jasperReport.
            //            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
            ////            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
            //
            //            //// Compilar o relatório JRXML
            //            JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, beanDataSource );
            //            //   new JREmptyDataSource() //  é um fornecedor de dados vazio.
            //
            //            // Configurar a resposta HTTP para um PDF
            //            response.setContentType("application/pdf");
            //            // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e como o navegador deve manipulá-lo.
            //            // Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador, e "filename=relatorio.pdf" define o nome do arquivo.
            //            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
            //
            //            // Exportar o relatório para um fluxo de saída
            //            // obtém um fluxo de saída a partir do objeto
            //            // usado para exportar o relatório no formato PDF.
            //            OutputStream outStream = response.getOutputStream();
            //            JRPdfExporter exporter = new JRPdfExporter();
            //
            //            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
            //            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            //
            //            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
            //            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            //
            //            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
            //            exporter.exportReport();
            //
            //            //// Visualizar o relatório
            //            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            //            viewer.setVisible(true);
            //
            //            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            //            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            //            outStream.flush(); //// Isso limpa qualquer buffer de saída pendente.
            //            outStream.close(); ////  Isso fecha o fluxo de saída.


            //            var findByAllUsers = usuarioFlywayService.findByAll();
            //            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(findByAllUsers);

            //            Map<String, Object> parameters = new HashMap<>();
            //            parameters.put("tituloRelatorio", "Relatório de Produtos");
            //            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            //
            //            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
            //
            //            JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, beanDataSource);
            //
            //            // Salvar o relatório em um arquivo PDF no servidor
            //            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            //            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            //            // Visualizar o relatório com o JasperViewer
            //            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            //            // Configure o fechamento da janela para encerrar o aplicativo
            //            viewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
            //            viewer.setVisible(true);


            var findByAllUsers = usuarioFlywayService.findByAll();
            // Criar a fonte de dados JRBeanCollectionDataSource
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findByAllUsers);

            UsuarioFlyway userFly = new UsuarioFlyway();
            //    for (UsuarioFlyway findByAllUser : findByAllUsers) {
            //        userFly.setUsuarioEnumTypeEnum(findByAllUser.getUsuarioEnumTypeEnum());
            //        userFly.getUsuarioEnumAsString(userFly.getUsuarioEnumTypeEnum());
            //
            //    }

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            // Create a list of Enum values
            //   List<UsuarioEnumType> enumList = Arrays.asList(UsuarioEnumType.values());
            // Create a list of Strings from the enum values
            //    List<String> dados = Arrays.asList("N", "E");
            // List<UsuarioEnumType> enumNames = new ArrayList<>();

            //  String enumNames = " ";
            //            for (UsuarioEnumType item : UsuarioEnumType.values()) {
            //                    enumNames.add(item.name());
            //            }

            //  for (UsuarioEnumType dado : UsuarioEnumType.values()) {
            //        if ("N".equals(dado)) {
            //            enumNames.add(UsuarioEnumType.N);
            //        } else if ("E".equals(dado)) {
            //            enumNames.add(UsuarioEnumType.E);
            //        }
            //    }
            //  }

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            UsuarioFlyway usuarioFlyway = new UsuarioFlyway();

            //            for (int i = 0; i < findByAllUsers.size(); i++) {
            //                parameters.put("usuario_enum_type_enum", findByAllUsers.get(i).getUsuarioEnumTypeEnum().name());
            //            };

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                if(usuario.getUsuarioEnumTypeEnum() != null) {
                    parametersMap.put("usuarioEnumTypeEnum", usuario.getUsuarioEnumTypeEnum().name());
                }

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Adicione a lista de enum ao parâmetro
            // parameters.put(, usuarioFlyway.getUsuarioEnumTypeEnum().name());

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Configurar a rparameters =  size = 12esposta HTTP para um PDF
            // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e como o navegador deve manipulá-lo.
            // Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador,
            // e "filename=relatorio.pdf" define o nome do arquivo.
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            OutputStream outStream = response.getOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath );

            // Visualizar o relatório
            //    JasperViewer viewer = new JasperViewer(jasperPrint, false);
            //    viewer.setVisible(true);

        } catch (CustomException e) {
            System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        catch (IOException e) {
//            throw new RuntimeException("Erro de E/S: " + e.getMessage(), e);
//        }


    }
}
