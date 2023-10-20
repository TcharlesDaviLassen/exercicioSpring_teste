package com.example.exercicio.controller;

import com.example.exercicio.entities.Images;
import com.example.exercicio.repository.ImagemRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemController {
    @Autowired
    private ImagemRepository imagemRepository;

    @PostMapping("/upload")
    public String uploadImagem(@RequestParam("file") List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {
            try {
                Images imagem = new Images();
                imagem.setNome(file.getOriginalFilename());
                imagem.setDados(file.getBytes());
                imagemRepository.save(imagem);

                // Agora você pode copiar a imagem para o servidor
                // Você pode usar o código de cópia anterior para copiar a imagem para um diretório específico
            } catch (IOException e) {
                e.printStackTrace();
                return "Erro ao fazer upload das imagens: " + e.getMessage();
            }
        }

        return "Imagens salvas com sucesso.";

        //        Images imagem = new Images();
        //        imagem.setNome(file.getName());
        //        imagem.setDados(file.getBytes());
        //        imagemRepository.save(imagem);
        //
        //        // Aqui você pode copiar a imagem para o servidor
        //        // Por simplicidade, estou apenas retornando a ID da imagem
        //
        //
        //        return "Imagem salva com sucesso. ID: " + imagem.getId();
    }

//    @GetMapping("/download/{id}")
    @PostMapping("/downloadImagem")
    public String downloadImagem(@RequestBody Long id, @RequestBody String destinationDirectory) {

//        for (Long id : ids) {
            Images imagem = imagemRepository.findById(id).orElse(null);
            if (imagem != null) {
                String destinationPath = destinationDirectory + "/" + imagem.getNome();
                try (FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
                    outputStream.write(imagem.getDados());
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Erro ao salvar a imagem: " + e.getMessage();
                }
            }
//        }

        return "Imagens baixadas e salvas com sucesso no diretório: " + destinationDirectory;

        //        Images imagem = imagemRepository.findById(id).orElse(null);
        //        if (imagem != null) {
        //            return imagem.getDados();
        //        }
        //        return null;
    }

    @GetMapping("/download/{imageUrl}/{destinationPath}")
    public String getDownloadImage(@PathVariable String imageUrl, @PathVariable String destinationPath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(imageUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    try (InputStream inputStream = response.getEntity().getContent()) {
                        try (FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
                            int bytesRead;
                            byte[] buffer = new byte[1024];
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }
                        return "Imagem baixada com sucesso.";
                    }
                } else {
                    return "Falha ao baixar a imagem. Código de resposta HTTP: " + response.getStatusLine().getStatusCode();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro durante o download da imagem: " + e.getMessage();
        }
    }


    @PostMapping("/downloadsImages")
    public String postDownloadImages(@RequestBody List<String> imageUrls, @RequestBody String destinationDirectory) {
        File directory = new File(destinationDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int count = 1;

        for (String imageUrl : imageUrls) {
            String destinationPath = destinationDirectory + "/image" + count + ".jpg";
            count++;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(imageUrl);
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        try (InputStream inputStream = response.getEntity().getContent()) {
                            try (FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
                                int bytesRead;
                                byte[] buffer = new byte[1024];
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                            }
                        }
                    } else {
                        return "Falha ao baixar a imagem. Código de resposta HTTP: " + response.getStatusLine().getStatusCode();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Erro durante o download das imagens: " + e.getMessage();
            }
        }

        return "Imagens baixadas com sucesso.";
    }
}
