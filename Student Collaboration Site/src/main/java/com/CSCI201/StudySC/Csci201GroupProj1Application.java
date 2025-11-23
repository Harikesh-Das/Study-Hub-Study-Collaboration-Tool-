package com.CSCI201.StudySC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.CSCI201.StudySC")
public class Csci201GroupProj1Application {

	public static void main(String[] args) {
		SpringApplication.run(Csci201GroupProj1Application.class, args);
	}

}
 