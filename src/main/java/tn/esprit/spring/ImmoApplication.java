package tn.esprit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAspectJAutoProxy
@EnableScheduling
@EnableWebSecurity
@SpringBootApplication
public class ImmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImmoApplication.class, args);
	}

}
