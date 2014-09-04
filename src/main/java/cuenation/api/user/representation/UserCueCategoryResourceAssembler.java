package cuenation.api.user.representation;

import cuenation.api.cue.domain.CueCategory;
import cuenation.api.user.UserCueCategoryController;
import cuenation.api.user.domain.User;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserCueCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, UserCueCategoryResource> {

    public UserCueCategoryResourceAssembler() {
        super(UserCueCategoryController.class, UserCueCategoryResource.class);
    }

    @Override
    public UserCueCategoryResource toResource(CueCategory category) {
        return new UserCueCategoryResource(category);
    }

    public ResourceSupport getResponse(User user, List<CueCategory> categories) {
        List<UserCueCategoryResource> categoryResources = toResources(categories);
        Resources resource = Resources.wrap(categoryResources);

        resource.add(ControllerLinkBuilder.linkTo(methodOn(UserCueCategoryController.class).list(user.getToken())).withSelfRel());

        return resource;
    }

}
