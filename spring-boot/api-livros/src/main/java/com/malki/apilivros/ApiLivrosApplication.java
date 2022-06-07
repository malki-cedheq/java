package com.malki.apilivros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) //disabilita tela de erro padrão
@RestController
public class ApiLivrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiLivrosApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "API LIVROS!";
	}
}
