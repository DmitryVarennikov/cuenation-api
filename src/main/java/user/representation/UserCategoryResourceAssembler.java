package user.representation;

import cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import user.UserCategoryController;

import java.util.List;

@Component
public class UserCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, UserCategoryResource> {

    public UserCategoryResourceAssembler() {
        super(UserCategoryController.class, UserCategoryResource.class);
    }

    @Override
    public UserCategoryResource toResource(CueCategory category) {
        return new UserCategoryResource(category);
    }

    public ResourceSupport getResponse(List<CueCategory> categories) {
        List<UserCategoryResource> categoryResources = toResources(categories);
        return Resources.wrap(categoryResources);
    }

}
