package myproject.motospring.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"myproject.motospring.gestion"})
public class GestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionApplication.class, args);
	}

}
