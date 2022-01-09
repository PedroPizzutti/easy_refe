package io.github.pedropizzutti.acervo_referencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AcervoReferenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcervoReferenciasApplication.class, args);
	}

}

