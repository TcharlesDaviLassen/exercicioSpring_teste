package com.example.exercicio.repository;

import com.example.exercicio.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<Images, Long> { }
