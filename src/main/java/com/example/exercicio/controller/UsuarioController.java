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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
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

    // Teste EmbeddedId, incorpora na chave ID da entidade chaves campostas
    // adicionais.
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

    // Teste Embedded, incorpora no corpo da entidade valores campos campostos
    // adicionais.
    @Autowired
    private AddressService addressService;

    @GetMapping("/executeOrderIdEmbedded")
    private void executeOrderIdEmbedded() {
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
            // Coloque aqui o código que pode lançar BusinessException
            // String errorMsg = String.format("Erro no mapa de totais. Argumento 1: %s,
            // Argumento 2: %s", arg1, arg2);;
            throw new BusinessException(true, "error.mapa.totais", arg1, arg2);
        } catch (BusinessException ex) {
            model.addAttribute("errorMsg", ex.getMessage());

            var mess = new BusinessException(true, "error.mapa.totais.info", arg1, arg2, arg3);
            model.addAttribute("errorMsgInfo", mess.getMessage());

            return "error";
        }
    }

    // @ExceptionHandler(BusinessException.class)
    // public ResponseEntity<String> handleBusinessException(BusinessException ex) {
    // return ResponseEntity.badRequest().body(ex.getMessage());
    // }

    // @RequestMapping(value = "/jasperPDF",
    // consumes = MediaType.APPLICATION_JSON_VALUE,
    // produces = MediaType.APPLICATION_JSON_VALUE,
    // method = RequestMethod.POST
    // )
    @RequestMapping(value = "/jasperPDF", method = RequestMethod.GET)
    public void jasperPDF(HttpServletResponse response) {
        try {
            // // Obter os dados a serem incluídos no relatório
            // var findByAllUsers = usuarioFlywayService.findByAll();
            //
            // // Criar uma fonte de dados a partir dos dados dos usuários
            // JRBeanCollectionDataSource beanDataSource = new
            // JRBeanCollectionDataSource(findByAllUsers);
            //
            // // Definir parâmetros que podem ser usados no relatório
            // Map<String, Object> parameters = new HashMap<>();
            // parameters.put("tituloRelatorio", "Relatório de Produtos");
            // parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho
            // spring teste");
            //
            // // Especificar o caminho do arquivo JRXML do relatório
            // String reportTemplatePath =
            // "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jrxml";
            //
            // // Compilar o relatório a partir do arquivo JRXML
            // JasperReport jasperReport =
            // JasperCompileManager.compileReport(reportTemplatePath);
            //
            // // Preencher o relatório com dados
            // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
            // parameters, beanDataSource);
            //
            // // Configurar a resposta HTTP para um PDF
            // response.setContentType("application/pdf");
            // response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
            //
            // // Exportar o relatório para um fluxo de saída
            // OutputStream outStream = response.getOutputStream();
            // JRPdfExporter exporter = new JRPdfExporter();
            // exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            // exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
            // exporter.exportReport();
            //
            // // Exportar o relatório para um arquivo PDF no servidor
            // String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" +
            // UUID.randomUUID().toString() + "_usuarios.pdf";
            // JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            // outStream.flush();
            // outStream.close();

            // var findByAllUsers = usuarioFlywayService.findByAll();
            // List<UsuarioFlyway> usuarioAdd = new ArrayList<>();
            //
            // UsuarioFlyway usuario = new UsuarioFlyway();
            //
            // for (var i = 0; i < findByAllUsers.size(); i++) {
            // usuario.setNome(findByAllUsers.get(i).getNome());
            // usuario.setEmail(findByAllUsers.get(i).getEmail());
            // usuario.setData(findByAllUsers.get(i).getData());
            //
            // usuarioAdd.add(usuario);
            //
            // //// Converter a lista de findByAllUsers em um JRBeanCollectionDataSource
            // //// JRBeanCollectionDataSource: Esta é uma classe do JasperReports que
            // permite criar uma fonte de dados a partir de uma coleção de objetos Java.
            // JRBeanCollectionDataSource beanDataSource = new
            // JRBeanCollectionDataSource(usuarioAdd);
            //
            // String caminhoImagem = "/home/tcharles/Imagens/Capturas de tela/Captura de
            // tela de 2023-06-08 11-33-45.png";
            //
            // Map<String, Object> parameters = new HashMap<>();
            // parameters.put("tituloRelatorio", "Relatório de Produtos");
            // parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho
            // spring teste");
            // parameters.put("caminhoImagem", caminhoImagem);
            // for (int i = 0; i < findByAllUsers.size(); i++) {
            // parameters.put("usuario_enum_type_enum",
            // findByAllUsers.get(i).getUsuarioEnumTypeEnum());
            // }
            //
            // //// para compilar o relatório JasperReports. O método compileReport é usado
            // para compilar o arquivo JRXML no caminho especificado em reportTemplatePath.
            // O resultado compilado é armazenado na variável jasperReport.
            // String reportTemplatePath =
            // "/home/tcharles/JaspersoftWorkspace/MyReports/jasperPDF_teste.jrxml";
            // JasperReport jasperReport =
            // JasperCompileManager.compileReport(reportTemplatePath);
            //
            // //// Compilar o relatório JRXML
            // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
            // parameters, beanDataSource );
            // // new JREmptyDataSource() // é um fornecedor de dados vazio.
            //
            // // Configurar a resposta HTTP para um PDF
            //// response.setContentType("application/pdf");
            //
            // // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo
            // e como o navegador deve manipulá-lo.
            // // Neste caso, "inline" indica que o navegador deve exibir o PDF no
            // navegador, e "filename=relatorio.pdf" define o nome do arquivo.
            //// response.setHeader("Content-Disposition", "inline;
            // filename=relatorio.pdf");
            //
            // // Exportar o relatório para um fluxo de saída
            //// OutputStream outStream = response.getOutputStream();
            //
            // // obtém um fluxo de saída a partir do objeto
            // // usado para exportar o relatório no formato PDF.
            //// JRPdfExporter exporter = new JRPdfExporter();
            //
            // // Aqui, definimos o relatório a ser exportado como entrada para o
            // exportador.
            //// exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            //
            // // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o
            // outStream
            //// exporter.setExporterOutput(new
            // SimpleOutputStreamExporterOutput(outStream));
            //
            // // Isso efetivamente exporta o relatório para o fluxo de saída, criando o
            // PDF.
            //// exporter.exportReport();
            //
            // //// Visualizar o relatório
            //// JasperViewer viewer = new JasperViewer(jasperPrint, false);
            //// viewer.setVisible(true);
            //
            // String filePath = "/home/tcharles/Área de
            // Trabalho/Spring-Boot_Exemples/exercicioSpring_teste/PDFS/" +
            // UUID.randomUUID().toString() + "_usuarios.pdf";
            // JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            //// outStream.flush(); //// Isso limpa qualquer buffer de saída pendente.
            //// outStream.close(); //// Isso fecha o fluxo de saída.

            // // Obtenha a lista de usuários
            // List<UsuarioFlyway> usuarios = usuarioFlywayService.findByAll();
            //
            // // Crie uma lista para armazenar os usuários a serem usados no relatório
            // List<UsuarioFlyway> usuariosParaRelatorio = new ArrayList<>();
            //
            // // Preencha a lista 'usuariosParaRelatorio' com os dados dos usuários
            // for (UsuarioFlyway usuario : usuarios) {
            // UsuarioFlyway usuarioRelatorio = new UsuarioFlyway();
            // usuarioRelatorio.setNome(usuario.getNome());
            // usuarioRelatorio.setEmail(usuario.getEmail());
            // usuarioRelatorio.setData(usuario.getData());
            // usuarioRelatorio.setUsuarioEnumTypeEnum(usuario.getUsuarioEnumTypeEnum());
            // usuariosParaRelatorio.add(usuarioRelatorio);
            // }
            //
            // // Converta a lista 'usuariosParaRelatorio' em um JRBeanCollectionDataSource
            // JRBeanCollectionDataSource beanDataSource = new
            // JRBeanCollectionDataSource(usuariosParaRelatorio);
            //
            // // Outros parâmetros do relatório
            // String caminhoImagem = "/home/tcharles/Imagens/Capturas de tela/Captura de
            // tela de 2023-06-08 11-33-45.png";
            // Map<String, Object> parameters = new HashMap<>();
            // parameters.put("tituloRelatorio", "Relatório de Usuários");
            // parameters.put("subTituloRelatorio", "Relatório de Usuários do Spring Boot");
            // parameters.put("caminhoImagem", caminhoImagem);
            //
            // // para compilar o relatório JasperReports
            // String reportTemplatePath =
            // "/home/tcharles/JaspersoftWorkspace/MyReports/jasperPDF_teste.jrxml";
            // JasperReport jasperReport =
            // JasperCompileManager.compileReport(reportTemplatePath);
            //
            // // Compilar o relatório JRXML
            // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
            // parameters, beanDataSource);
            //
            // // Exportar o relatório para um arquivo PDF
            // String filePath = "/home/tcharles/Área de
            // Trabalho/Spring-Boot_Exemples/exercicioSpring_teste/PDFS/" +
            // UUID.randomUUID().toString() + "_usuarios.pdf";
            // JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            // var findByAllUsers = usuarioFlywayService.findByAll();
            // JRBeanCollectionDataSource beanDataSource = new
            // JRBeanCollectionDataSource(findByAllUsers);

            // Map<String, Object> parameters = new HashMap<>();
            // parameters.put("tituloRelatorio", "Relatório de Produtos");
            // parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho
            // spring teste");
            //
            // String reportTemplatePath =
            // "/home/flexabus/devel/workspaces/flexabus-external-resources/reports/TESTE/jasperPDF_teste.jasper";
            //
            // JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath,
            // parameters, beanDataSource);
            //
            // // Salvar o relatório em um arquivo PDF no servidor
            // String filePath = "/home/flexabus/Downloads/ExercicoSpring_teste/PDF_Docs/" +
            // UUID.randomUUID().toString() + "_usuarios.pdf";
            // JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
            //
            // // Visualizar o relatório com o JasperViewer
            // JasperViewer viewer = new JasperViewer(jasperPrint, false);
            // // Configure o fechamento da janela para encerrar o aplicativo
            // viewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
            // viewer.setVisible(true);

            var findByAllUsers = usuarioFlywayService.findByAll();
            // Converta a lista de objetos em JSON
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonUsuarios = objectMapper.writeValueAsString(findByAllUsers);

//            // Crie um objeto Gson
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            try (FileWriter writer = new FileWriter("/home/tcharles/Área de Trabalho/Spring-Boot_Exemples/exercicioSpring_teste/ImagesDownloadPSQL/output.json")) {
//                // Converte o objeto em JSON e escreve no arquivo
//                gson.toJson(jsonUsuarios, writer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            String reportTemplatePath = "/home/tcharles/JaspersoftWorkspace/MyReports/jasperPDF_teste.jrxml";

            // UsuarioFlyway usuarioEnumType = new UsuarioFlyway();
//            List<String> enumValue = new ArrayList<>();

//            for (UsuarioEnumType dado : UsuarioEnumType.values()) {
//                for (int i = 0; i < findByAllUsers.size(); i++) {
//                    if (findByAllUsers.get(i).getUsuarioEnum() != null) {
//                        enumValue.add(dado.name());
//                    }
//                }
//            }

            // for (int i = 0; i < findByAllUsers.size(); i++) {
            // usuarioEnumType.setUsuarioEnumTypeEnum(findByAllUsers.get(i).getUsuarioEnumTypeEnum());
            // }

            // Carregar e compilar o JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

            // Criar a fonte de dados JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findByAllUsers);

//            ArrayList<String> users = new ArrayList<>();
//            for (UsuarioEnumType datum : UsuarioEnumType.values()) {
//                users.add(datum.name());
//            }

            // Preencher os parâmetros do relatório, se houver
            // Map<String, Object> parameters = new HashMap<>();
            // parameters.put("tituloRelatorio", "Relatório de Produtos");
            // parameters.put("subTituloRelatorio", "Relatório de Produtos do trabalho
            // spring teste");
            // parameters.put("usuario_enum",
            // usuarioFlywayService.getEnumListFromDatabase());

            // List<String> enumNames = Arrays.stream(UsuarioEnumType.values())
            // .map(Enum::name)
            // .toList();
            // parameters.put("usuario_enum_type_enum", enumNames.get(1).equals("E") ? "E" :
            // "N"); ;

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tituloRelatorio", "Relatório de Produtos");
            parametros.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
//            for (UsuarioFlyway usuario : findByAllUsers) {
//                parametros.put("id", usuario.getId());
//                parametros.put("nome", usuario.getNome());
//                parametros.put("numero", usuario.getNumero());
//                parametros.put("email", usuario.getEmail());
//                parametros.put("data", usuario.getData());
//                // parametros.put("usuarioEnum", usuario.getUsuarioEnum());
//
//                if (usuario.getUsuarioEnum() != null) {
//                    parametros.put("usuarioEnum", usuario.getUsuarioEnum().name());
//                }
//            }

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JsonDataSource(jsonUsuarios));


            // Configurar a resposta HTTP para um PDF
            // Isso configura o cabeçalho da resposta HTTP para definir o nome do arquivo e
            // como o navegador deve manipulá-lo.
            // Neste caso, "inline" indica que o navegador deve exibir o PDF no navegador, e
            // "filename=relatorio.pdf" define o nome do arquivo.
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            // Exportar o relatório para um fluxo de saída
            OutputStream outStream = response.getOutputStream();

            // obtém um fluxo de saída a partir do objeto
            // usado para exportar o relatório no formato PDF.
            JRPdfExporter exporter = new JRPdfExporter();

            // Aqui, definimos o relatório a ser exportado como entrada para o exportador.
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            // Configuramos o fluxo de saída para o exportador, que enviará o PDF para o
            // outStream
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));

            // Isso efetivamente exporta o relatório para o fluxo de saída, criando o PDF.
            exporter.exportReport();

            // Exportar o relatório para um arquivo PDF
            String filePath = "/home/tcharles/Área de Trabalho/Spring-Boot_Exemples/exercicioSpring_teste/PDFS/"
                    + UUID.randomUUID().toString() + "_usuarios.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            // // Visualizar o relatório
            // JasperViewer viewer = new JasperViewer(jasperPrint, false);
            // viewer.setVisible(true);

        } catch (CustomException e) {
            System.out.println(
                    "ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // catch (IOException e) {
        // throw new RuntimeException("Erro de E/S: " + e.getMessage(), e);
        // }

    }
}
