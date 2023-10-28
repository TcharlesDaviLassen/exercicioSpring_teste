package com.example.exercicio.controller;

import com.example.exercicio.entities.ProdutoTeste;
import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.TipoProdutoType;
import com.example.exercicio.errorsUtils.customRuntimeExempion.CustomException;
import com.example.exercicio.service.TipoProdutoService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/teste")
public class TipoProduto {

    private final TipoProdutoService tipoProdutoService;

    public TipoProduto(TipoProdutoService tipoProdutoService) {
        this.tipoProdutoService = tipoProdutoService;
    }

    @RequestMapping(value = "/jasperPDF", method = RequestMethod.GET)
    public void jasperPDF(HttpServletResponse response) {
        try {

            //    // Suponha que você tenha uma lista de objetos ProdutoTeste em findByAllUsers
            //    List<ProdutoTeste> listaDeProdutos = tipoProdutoService.findAllTeste();
            //
            //    String reportTemplatePath = "/home/tcharles/JaspersoftWorkspace/MyReports/tipoProduto.jrxml";
            //
            //    // Carregar e compilar o JRXML
            //    JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);
            //
            //    Map<String, Object> parametros = new HashMap<>();
            //    parametros.put("tituloRelatorio", "Relatório de Produtos");
            //    parametros.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");
            //
            //    // Passe a lista de dados como parâmetro
            //    parametros.put("dadosProdutos", listaDeProdutos);

            // Preencha o relatório com os parâmetros
            //    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());



            var findByAllUsers = tipoProdutoService.findAllTeste();
            String reportTemplatePath = "/home/tcharles/JaspersoftWorkspace/MyReports/tipoProduto.jrxml";

            // Carregar e compilar o JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplatePath);

            //            List<String> listaParametrosType = new ArrayList<>();
            //            for (ProdutoTeste usuario : findByAllUsers) {
            //                ProdutoTeste produtoTeste = new ProdutoTeste();
            //                produtoTeste.setTipoProdutoType(usuario.getTipoProdutoType());
            //            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tituloRelatorio", "Relatório de Produtos");
            parametros.put("subTituloRelatorio", "Relatório de Produtos do trabalho spring teste");

            List<Map<String, Object>> listaParametros = new ArrayList<>();
            for (ProdutoTeste usuario : findByAllUsers) {
                Map<String, Object> parametrosMap = new HashMap<>();
                parametrosMap.put("nome", usuario.getNome());
                parametrosMap.put("preco", usuario.getPreco());
                parametrosMap.put("tipoProdutoType", usuario.getTipoProdutoType());

                listaParametros.add(parametrosMap);
            }

            // Criar a fonte de dados JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaParametros);

            // Preencher o relatório com os dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

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
            String filePath = "/home/tcharles/Área de Trabalho/Spring-Boot_Exemples/exercicioSpring_teste/PDFS/" + UUID.randomUUID().toString() + "tipoProduto.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

        } catch (CustomException e) {
            System.out.println(
                    "ERRO NO JASPERSOFT NA CLASSE DE UsuarioController: " + e.getStatus() + " " + e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException("Erro no JasperReports: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/create")
    public @ResponseBody String criarRegistro(@RequestBody ProdutoTeste produtoTeste) {
        tipoProdutoService.createTeste(produtoTeste);

        return "redirect:/usuario/usuarioPage";
    }

}
