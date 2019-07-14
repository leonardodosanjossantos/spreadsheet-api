package br.com.leonardo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.leonardo.CommonsConfig;
import br.com.leonardo.RabbitMQConfig;

@Import(value = {CommonsConfig.class, RabbitMQConfig.class})
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
