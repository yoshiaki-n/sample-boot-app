package com.example.springbootapp;

import org.springframework.boot.SpringApplication;

public class TestSpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringBootAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
