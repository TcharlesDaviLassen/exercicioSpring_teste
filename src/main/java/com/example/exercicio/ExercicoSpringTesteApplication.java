package com.example.exercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExercicoSpringTesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExercicoSpringTesteApplication.class, args);
		System.out.println("http://localhost:3333/usuario/usuarioPage");
	}

}
