package com.exams.microservices.appexamusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AppExamUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppExamUsersApplication.class, args);
	}

}
