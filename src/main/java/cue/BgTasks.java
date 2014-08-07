package cue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BgTasks {

    @Autowired
    private CueService cueService;

    @Scheduled(cron = "0 */10 * * * *")
    public void updateCues() {
        cueService.updateCues();
    }

}
