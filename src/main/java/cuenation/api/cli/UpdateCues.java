package cuenation.api.cli;

import cuenation.api.cue.service.CueService;
import cuenation.api.user.UserCueController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = {"cuenation.api.cue"})
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = {"cuenation.api.cue"})
public class UpdateCues implements CommandLineRunner {

    final private Logger logger = LoggerFactory.getLogger(UserCueController.class);

    @Autowired
    private CueService cueService;

    public static void main(String[] args) {
        SpringApplication.run(UpdateCues.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int cuesNumber = cueService.updateCues();

        String message = String.format("%d cues added", cuesNumber);
        logger.info(message);

        System.exit(0);
    }

}
