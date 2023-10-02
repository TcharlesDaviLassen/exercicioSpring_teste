package com.example.exercicio.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class ArmazenamentoDocumentoFiscalService {

    private static final String DIRETORIO_DOCUMENTOS = "diretorio_documentos_fiscais";

    public void armazenarDocumento(byte[] documento) throws IOException {
        File diretorio = new File(DIRETORIO_DOCUMENTOS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String nomeArquivo = "documento_" + System.currentTimeMillis() + ".pdf";
        String caminhoCompleto = DIRETORIO_DOCUMENTOS + File.separator + nomeArquivo;

        try (FileOutputStream fos = new FileOutputStream(caminhoCompleto)) {
            fos.write(documento);
        }

        // Implemente a lógica real de armazenamento seguro de documentos fiscais aqui
    }
}