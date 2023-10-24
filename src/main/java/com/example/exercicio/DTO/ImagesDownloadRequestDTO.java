package com.example.exercicio.DTO;

import java.util.List;

public class ImagesDownloadRequestDTO {
    private Long id;

    private List<String> imageUrls;
    private String destinationDirectory;

    // Getters e setters para 'id' e 'destinationDirectory'


    public ImagesDownloadRequestDTO() {
    }

    public ImagesDownloadRequestDTO(Long id, String destinationDirectory) {
        this.id = id;
        this.destinationDirectory = destinationDirectory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
