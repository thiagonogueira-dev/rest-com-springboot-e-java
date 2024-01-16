package br.com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
			.title("RESTful API com Java 21 e Spring Boot 3")
			.version("v1")
			.description("Descrição da API")
			.termsOfService("https://thiagonogueira.vercel.app")
			.license(
				new License()
				.name("Apache 2.0")
				.url("https://thiagonogueira.vercel.app")
				)
			);
	}
	
}
