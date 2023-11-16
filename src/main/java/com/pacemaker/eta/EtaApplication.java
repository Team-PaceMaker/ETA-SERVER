package com.pacemaker.eta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class EtaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EtaApplication.class, args);
	}
}
