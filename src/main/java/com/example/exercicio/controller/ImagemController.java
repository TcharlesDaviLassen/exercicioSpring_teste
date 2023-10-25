package com.example.exercicio.controller;

import com.example.exercicio.DTO.ImagesDownloadRequestDTO;
import com.example.exercicio.entities.Images;
import com.example.exercicio.repository.ImagemRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

//@RestController
//@RequestMapping("/imagens")
//public class ImagemController {
//    @Autowired
//    private ImagemRepository imagemRepository;
//
//    @PostMapping("/uploads")
//    public String uploadImagem(@RequestParam("file") List<MultipartFile> files) throws IOException {
//
//        for (MultipartFile file : files) {
//            try {
//                Images imagem = new Images();
//                imagem.setNome(file.getOriginalFilename());
//                imagem.setDados(file.getBytes());
//                imagemRepository.save(imagem);
//
//                // Agora você pode copiar a imagem para o servidor
//                // Você pode usar o código de cópia anterior para copiar a imagem para um diretório específico
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Erro ao fazer upload das imagens: " + e.getMessage();
//            }
//        }
//
//        return "Imagens salvas com sucesso.";
//    }

@Controller
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping("/page")
    public String renderPageImage(Model model) {
        return "imageUpload";
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public String uploadImagem(@RequestParam("file") List<MultipartFile> files, Model model) {
        for (MultipartFile file : files) {
            try {
                String  filename = file.getOriginalFilename();
                if(filename.isEmpty()) {
                    throw new IOException("Nome do arquivo em branco.");
                }

                Images imagem = new Images();
                imagem.setNome(file.getOriginalFilename());
                imagem.setDados(file.getBytes());
                imagemRepository.save(imagem);

                model.addAttribute("message", "Imagens salvas com sucesso.");

            } catch (IOException  e) {
                e.printStackTrace();
                model.addAttribute("message", "Erro ao fazer upload das imagens: " + e.getMessage() + " causa: " + e.getCause());
                return "imageUpload";
            }
        }

        return "imageUpload";
    }


    @PostMapping("/downloadImagem")
    public String downloadImagem(@RequestBody ImagesDownloadRequestDTO imagesDownloadRequestDTO) {
        Long id = imagesDownloadRequestDTO.getId();
        String destinationDirectory = imagesDownloadRequestDTO.getDestinationDirectory();

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

        return "Imagens baixadas e salvas com sucesso no diretório: " + destinationDirectory;
    }


    @PostMapping("/downloadDeTodasAsImagens")
    public String downloadDeTodasAsImagens(@RequestBody ImagesDownloadRequestDTO imagesDownloadRequestDTO) {
        String destinationDirectory = imagesDownloadRequestDTO.getDestinationDirectory();

        List<Images> imagens = imagemRepository.findAll();

        for (Images imagem : imagens) {
            if (imagem != null) {
                String destinationPath = destinationDirectory + "/" + imagem.getNome();
                try (FileOutputStream outputStream = new FileOutputStream(destinationPath)) {
                    outputStream.write(imagem.getDados());
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Erro ao salvar a imagem: " + e.getMessage();
                }
            }
        }
        return "Imagens baixadas e salvas com sucesso no diretório: " + destinationDirectory;
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
    public String postDownloadImages(@RequestBody ImagesDownloadRequestDTO responseDTO) {

        List<String> imageUrls = responseDTO.getImageUrls();
        String destinationDirectory = responseDTO.getDestinationDirectory();

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

    @PostMapping("/postDownloadImagesExtension")
    public String postDownloadImagesExtension(@RequestBody ImagesDownloadRequestDTO responseDTO) {
        List<String> imageUrls = responseDTO.getImageUrls();
        String destinationDirectory = responseDTO.getDestinationDirectory();

        File directory = new File(destinationDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int count = 0;

        for (String imageUrl : imageUrls) {
            String extension = imageUrl.substring(imageUrl.lastIndexOf("."));
            String destinationPath = destinationDirectory + "/image" + UUID.randomUUID().toString() + extension.substring(0 , 4);
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

    @PostMapping("/lerTodasAsImagensDeUmaPasta")
    public String lerTodasAsImagensDeUmaPasta() throws IOException {
        int totdasImagens = 0;
        int imagensPorTamanho = 0;

        String pathLocalhostDirectoryPath = "/home/tcharles/Imagens/Capturas de tela";
        List<File> imageFiles = getImagesInDirectory(pathLocalhostDirectoryPath);

        for (File imageFile : imageFiles) {
            var arraiDados =  imageFile.toString().split("\\.");
            var pngImage =  "."+arraiDados[1];

            if(pngImage.endsWith(".png")) {
                var tamanhoTotalImage = 6 * 1024 * 1024;

                if(imageFile.length() <= tamanhoTotalImage) {
                    System.out.println("Caminho absoluto: " + imageFile.getAbsolutePath());
                    System.out.println("Nome do arquivo: imagensPorTamanho " + imagensPorTamanho++ + " " + imageFile.getName());
                    System.out.println("Tamanho do arquivo: imagensPorTamanho " + imageFile.length() + " bytes");

                    Images imagem = new Images();
                    imagem.setNome(imageFile.getName());
                    File file = new File(String.valueOf(imageFile));
                    byte[] fileBytes = convertFileToByteArray(file);
                    imagem.setDados(fileBytes);

                    imagemRepository.save(imagem);
                } else {
                    System.out.println("Caminho absoluto: " + imageFile.getAbsolutePath());
                    System.out.println("Nome do arquivo: totdasImagens" + totdasImagens++ + " " + imageFile.getName());
                    System.out.println("Tamanho do arquivo: totdasImagens " + imageFile.length() + " bytes");
                }
            } else {
                return  "A imagem não é uma imagem válida, o formato deve ser um .png";
            }
        }

        return "Imagens lidas com sucesso.";
    }

    public static List<File> getImagesInDirectory(String directoryPath) {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (isImage(file)) {
                        imageFiles.add(file);
                    }
                }
            }
        }

        return imageFiles;
    }

    public static boolean isImage(File file) {
        try {
            // Tente ler o arquivo de imagem
            ImageIO.read(file);
            return true;
        } catch (IOException e) {
            // O arquivo não é uma imagem válida
            return false;
        }
    }

    public static byte[] convertFileToByteArray(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()]; // Tamanho do array igual ao tamanho do arquivo

        fileInputStream.read(fileBytes);
        fileInputStream.close();

        return fileBytes;
    }

}
