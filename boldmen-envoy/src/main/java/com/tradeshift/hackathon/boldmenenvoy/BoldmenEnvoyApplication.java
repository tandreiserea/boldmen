package com.tradeshift.hackathon.boldmenenvoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.tradeshift.hackathon.boldmenenvoy")
public class BoldmenEnvoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoldmenEnvoyApplication.class, args);
	}
}
