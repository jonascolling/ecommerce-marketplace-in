package com.ecommerce.marketplacein;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableFeignClients
public class MarketplaceInApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceInApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofSeconds(150))
				.setReadTimeout(Duration.ofSeconds(150))
				.build();
	}


}
