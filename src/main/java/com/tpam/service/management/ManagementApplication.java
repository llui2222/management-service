package com.tpam.service.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ManagementApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}
}
