package cuenation.api.user.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import cuenation.api.user.UserTokenController;
import cuenation.api.user.domain.User;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserTokenController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        return new UserResource(user);
    }

}
