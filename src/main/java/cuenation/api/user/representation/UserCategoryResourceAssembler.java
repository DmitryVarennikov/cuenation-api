package cuenation.api.user.representation;

import cuenation.api.cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import cuenation.api.user.UserCategoryController;
import cuenation.api.user.domain.User;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, UserCategoryResource> {

    public UserCategoryResourceAssembler() {
        super(UserCategoryController.class, UserCategoryResource.class);
    }

    @Override
    public UserCategoryResource toResource(CueCategory category) {
        return new UserCategoryResource(category);
    }

    public ResourceSupport getResponse(User user, List<CueCategory> categories) {
        List<UserCategoryResource> categoryResources = toResources(categories);
        Resources resource = Resources.wrap(categoryResources);

        resource.add(ControllerLinkBuilder.linkTo(methodOn(UserCategoryController.class).list(user.getToken())).withSelfRel());

        return resource;
    }

}
