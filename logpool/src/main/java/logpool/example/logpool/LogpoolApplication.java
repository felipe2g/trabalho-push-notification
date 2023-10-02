package logpool.example.logpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LogpoolApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogpoolApplication.class, args);
	}

}
