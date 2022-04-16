package com.ecommerce.marketplacein;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MarketplaceInApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceInApplication.class, args);
	}

}
