package cuenation.api.user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cuenation.api.cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "userCueCategories")
public class UserCueCategoryResource extends ResourceSupport {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("host")
    private String host;

    @JsonProperty("link")
    private String link;

    @JsonCreator
    public UserCueCategoryResource(CueCategory cueCategory) {
        id = cueCategory.getId();
        name = cueCategory.getName();
        host = cueCategory.getHost();
        link = cueCategory.getLink();
    }

}
