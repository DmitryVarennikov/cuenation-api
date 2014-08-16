package cuenation.api.user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import cuenation.api.user.UserCategoryController;
import cuenation.api.user.UserCueController;
import cuenation.api.user.UserTokenController;
import cuenation.api.user.domain.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

    @JsonProperty("token")
    String token;

    @JsonCreator
    public UserResource(User user) {
        token = user.getToken();

        add(ControllerLinkBuilder.linkTo(methodOn(UserTokenController.class).get(token)).withSelfRel());
        add(ControllerLinkBuilder.linkTo(methodOn(UserCategoryController.class).list(token)).withRel("userCueCategories"));
        add(ControllerLinkBuilder.linkTo(UserCueController.class, token).withRel("userCues"));
    }
}
