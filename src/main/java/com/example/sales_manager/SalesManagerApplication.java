package com.example.sales_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
	// exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
//@EnableTransactionManagement
public class SalesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesManagerApplication.class, args);
	}

}
