package user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

    @JsonProperty("id")
    String id;
    @JsonProperty("token")
    String token;

    @JsonCreator
    public UserResource(User user) {
        id = user.getId();
        token = user.getToken();

        add(linkTo(methodOn(UserController.class).get(token)).withSelfRel());
    }
}
