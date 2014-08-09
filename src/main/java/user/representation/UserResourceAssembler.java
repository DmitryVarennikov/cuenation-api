package user.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import user.UserTokenController;
import user.domain.User;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserTokenController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        return new UserResource(user);
    }

}
