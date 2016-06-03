package cuenation.api.user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.UserCue;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

@Relation(collectionRelation = "userCues")
public class UserCueResource extends ResourceSupport {

    @JsonProperty("id")
    private String id;

    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonCreator
    public UserCueResource(UserCue userCue) {
        Cue cue = userCue.getCue();

        // NOTE: this is not an original Cue ID but a UserCue Id!
        id = userCue.getId();
        // time of cues creation is duplicated for user cues in order to fetch them in a reverse order
        createdAt = userCue.getCreatedAt();
        // in order to know to which category a cue belongs to
        categoryId = userCue.getCategory().getId();

        title = cue.getTitle();
        link = cue.getLink();
    }

}
