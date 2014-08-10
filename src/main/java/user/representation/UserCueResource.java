package user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cue.domain.Cue;
import cue.domain.UserCue;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class UserCueResource extends ResourceSupport {

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonCreator
    public UserCueResource(UserCue userCue) {
        Cue cue = userCue.getCue();

        title = cue.getTitle();
        link = cue.getLink();
        createdAt = cue.getCreatedAt();
    }

}
