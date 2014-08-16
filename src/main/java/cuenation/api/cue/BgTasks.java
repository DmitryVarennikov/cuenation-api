package cuenation.api.cue;

import cuenation.api.cue.service.CueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BgTasks {

    Logger logger = LoggerFactory.getLogger(BgTasks.class);

    @Autowired
    private CueService cueService;

    @Scheduled(cron = "0 */10 * * * *")
    public void updateCues() {
        cueService.updateCues();

        String message = "Cues updated!";
        logger.info(message);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCueCategories() {
        cueService.updateCueCategories();

        String message = "Cue categories updated!";
        logger.info(message);
    }

}
