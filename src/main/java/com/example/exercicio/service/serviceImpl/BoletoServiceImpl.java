package com.example.exercicio.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.exercicio.entities.Boleto;
import com.example.exercicio.repository.BoletoRepository;
import com.example.exercicio.service.BoletoService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class BoletoServiceImpl implements BoletoService {

    private final BoletoRepository boletoRepository;

    public BoletoServiceImpl(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    public byte[] gerarBoletoPDF(Boleto boleto) throws Exception {
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("numero", boleto.getNumero());
        parametros.put("valor", boleto.getValor());
        parametros.put("vencimento", boleto.getVencimento());

        JasperReport jasperReport = JasperCompileManager.compileReport("caminho/para/seu/arquivo.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public Boleto criarBoleto(Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    @Override
    public List<Boleto> listarBoletos() {
        return (List<Boleto>) boletoRepository.findAll();
    }

}
