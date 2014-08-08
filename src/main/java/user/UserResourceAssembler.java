package user;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserTokenController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        return new UserResource(user);
    }

}
