package com.microlibrary.borrowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

import com.microlibrary.borrowservice.config.AxonConfig;



@SpringBootApplication
@EnableEurekaClient
@Import({ AxonConfig.class })
public class BorrowserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowserviceApplication.class, args);
	}

}
