package cuenation.api.user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cuenation.api.cue.domain.CueCategory;
import org.bson.types.ObjectId;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "userCueCategories")
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
