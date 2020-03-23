package com.oliynyk.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlayApplication {

	//docker run --rm --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword  postgres
	public static void main(String[] args) {
		SpringApplication.run(PlayApplication.class, args);
	}

}
