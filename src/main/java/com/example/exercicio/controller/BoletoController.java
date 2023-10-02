package com.example.exercicio.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.exercicio.entities.Boleto;
import com.example.exercicio.entities.DadosFiscais;
import com.example.exercicio.service.serviceImpl.BoletoServiceImpl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Controller
@RequestMapping("/boletos")
public class BoletoController {

    private final BoletoServiceImpl boletoServiceImpl;

    public BoletoController(BoletoServiceImpl boletoServiceImpl) {
        this.boletoServiceImpl = boletoServiceImpl;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boleto criarBoleto(@RequestBody Boleto boleto) {
        return boletoServiceImpl.criarBoleto(boleto);
    }

    @GetMapping
    public List<Boleto> listarBoletos() {
        return (List<Boleto>) boletoServiceImpl.listarBoletos();
    }

    @RequestMapping(value = "/realizarPagamento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> realizarPagamento(@RequestBody Boleto boleto) {
        // Simulação de integração com um serviço de pagamento fictício
        boolean pagamentoRealizado = PaymentService.realizarPagamento(boleto);

        if (pagamentoRealizado) {
            return ResponseEntity.ok("Pagamento realizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha no pagamento.");
        }
    }

    // Simulação de integração com um serviço de pagamento
    class PaymentService {
        public static boolean realizarPagamento(Boleto boleto) {
            // Implemente a lógica real de integração com o serviço de pagamento aqui
            if (boleto.getValor() > 0) {
                return true; // Pagamento bem-sucedido
            } else {
                return false; // Pagamento falhou
            }
        }
    }

    @RequestMapping(value = "/documento-fiscal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> emitirDocumentoFiscal(@RequestBody DadosFiscais dadosFiscais) throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph("Número: " + dadosFiscais.getNumero()));
        document.add(new Paragraph("Valor: " + dadosFiscais.getValor()));
        document.close();

        byte[] pdfBytes = outputStream.toByteArray();

        // Implemente a lógica real de armazenamento do documento fiscal aqui

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "documento_fiscal.pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
