package com.example.exercicio.controller;

import com.example.exercicio.DTO.User;
import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.Utils.classUtils.JsonDataSource;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
            
            var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            // Exportar o relatório para um fluxo de saída
            OutputStream outStream = response.getOutputStream();
            // obtém um fluxo de saída a partir do objeto usado para exportar o relatório no formato PDF.
            JRPdfExporter exporter = new JRPdfExporter();
            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o outStream
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/pdfDocs/" + UUID.randomUUID().toString() + "_usuarios.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath );

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída.

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

    @RequestMapping(value = "/jasperEXCEL", method = RequestMethod.GET)
    public void jasperEXCEL(HttpServletResponse response){
        try {

            var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png");

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.xlsx");

            OutputStream outStream = response.getOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/excelDocs/" + UUID.randomUUID().toString() + "_usuarios.xlsx";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída.

        } catch (CustomException e) {
            System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/jasperCSV", method = RequestMethod.GET)
    public void jasperCSV(HttpServletResponse response){
        try {

            var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.csv");

            OutputStream outStream = response.getOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(outStream));
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/csvDocs/" + UUID.randomUUID().toString() + "_usuarios.csv";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída.

        } catch (CustomException e) {
            System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/jasperHTML", method = RequestMethod.GET)
    public void jasperHTML(HttpServletResponse response){
        try {

            var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-09-19 10-55-58.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar o relatório

            // Converter o PDF em HTML usando Flying Saucer (iText Renderer)
            //            ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();
            //            ITextRenderer renderer = new ITextRenderer();
            //            renderer.setDocumentFromString(new String(pdfBytes));
            //            renderer.layout();
            //            renderer.createPDF(htmlStream, true);

            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.html");

            OutputStream outStream = response.getOutputStream();
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(outStream));
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/htmlDocs/" + UUID.randomUUID().toString() + "_usuarios.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, filePath);

            // Exportar o relatório para HTML
            // JasperExportManager.exportReportToHtmlFile(jasperPrint, "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/htmlDocs/" + UUID.randomUUID().toString() + "_relatorio.html");

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída.

        } catch (CustomException e) {
            System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/jasperXML", method = RequestMethod.GET)
    public void jasperXML(HttpServletResponse response){
        try {

            var findByAllUsers = usuarioFlywayService.findByAll();

            String reportTemplatePath = "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath); // Compila o relátorio .jrxml para um binário .jasper

            String contextImage = "/home/flexabus/Imagens/Capturas de tela/Captura de tela de 2023-10-09 08-44-11.png";

            //  Preencher os parâmetros do relatório, se houver
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloRelatorio", "Relatório de Produtos");
            parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            parameters.put("contextImage", contextImage);

            List<Map<String, Object>> lisMapParamiters = new ArrayList<>();
            for (UsuarioFlyway usuario : findByAllUsers) {
                Map<String, Object> parametersMap = new HashMap<>();

                parametersMap.put("nome", usuario.getNome());
                parametersMap.put("numero", usuario.getNumero());
                parametersMap.put("email", usuario.getEmail());
                parametersMap.put("data", usuario.getData());
                parametersMap.put("usuarioenumtypeenum", usuario.getUsuarioEnumTypeEnum().name());

                lisMapParamiters.add(parametersMap);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisMapParamiters);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/xml"); // Define o tipo de conteúdo para XML
            response.setHeader("Content-Disposition", "inline; filename=relatorio.xml"); // Define o nome do arquivo como relatorio.xml

            // Exportar o relatório para um fluxo de saída
            OutputStream outStream = response.getOutputStream();
            // Crie um exportador para o formato XML (JRXmlExporter)
            JRXmlExporter exporter = new JRXmlExporter();
            // Configure o exportador
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleXmlExporterOutput(outStream));
            // Exporte o relatório
            exporter.exportReport();

            String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/xmlDocs/" + UUID.randomUUID().toString() + "_usuarios.xml";
            JasperExportManager.exportReportToXmlFile(jasperPrint, filePath, true);

            outStream.flush(); // Isso limpa qualquer buffer de saída pendente.
            outStream.close(); //  Isso fecha o fluxo de saída.

        } catch (CustomException e) {
            System.out.println("ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
