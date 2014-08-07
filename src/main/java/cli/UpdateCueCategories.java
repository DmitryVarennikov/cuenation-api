package cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories
public class UpdateCueCategories implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UpdateCueCategories.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("UpdateCueCategories is running!");
        System.exit(0);
    }

}
