package user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;

public class UserCategoryResource extends ResourceSupport {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("link")
    private String link;

    @JsonCreator
    public UserCategoryResource(CueCategory cueCategory) {
        id = cueCategory.getId();
        name = cueCategory.getName();
        link = cueCategory.getLink();
    }

}
