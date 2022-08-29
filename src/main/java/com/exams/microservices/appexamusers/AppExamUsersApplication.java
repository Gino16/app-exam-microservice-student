package com.exams.microservices.appexamusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({ "com.exams.microservices.libcommonstudents.models.entities" })
public class AppExamUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppExamUsersApplication.class, args);
	}

}
