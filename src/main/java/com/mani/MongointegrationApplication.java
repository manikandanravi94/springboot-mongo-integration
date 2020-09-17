package com.mani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.mani.*")
public class MongointegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongointegrationApplication.class, args);
	}

}
