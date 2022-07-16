package com.cvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTransferApplication.class, args);
	}

}
