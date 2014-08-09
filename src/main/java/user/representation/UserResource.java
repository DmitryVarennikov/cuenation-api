package user.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import user.UserTokenController;
import user.domain.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

    @JsonProperty("token")
    String token;

    @JsonCreator
    public UserResource(User user) {
        token = user.getToken();

        add(ControllerLinkBuilder.linkTo(methodOn(UserTokenController.class).get(token)).withSelfRel());
    }
}
