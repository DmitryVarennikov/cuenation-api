package cuenation.api.cli;

import cuenation.api.cue.service.CueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = {"cuenation.api.cue"})
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = {"cue"})
public class UpdateCueCategories implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UpdateCueCategories.class, args);
    }

    @Autowired
    private CueService cueService;

    @Override
    public void run(String... args) throws Exception {
        int cueCategoriesNumber = cueService.updateCueCategories();

        System.out.println(String.format("%d cue categories added", cueCategoriesNumber));

        System.exit(0);
    }

}
