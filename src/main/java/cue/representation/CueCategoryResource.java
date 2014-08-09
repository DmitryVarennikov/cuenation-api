package cue.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;

public class CueCategoryResource extends ResourceSupport {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("link")
    private String link;

    @JsonCreator
    public CueCategoryResource(CueCategory cueCategory) {
        id = cueCategory.getId();
        name = cueCategory.getName();
        link = cueCategory.getLink();
    }

}
