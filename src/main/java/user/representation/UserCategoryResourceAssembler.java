package user.representation;

import cue.domain.CueCategory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import user.UserCategoryController;

public class UserCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, UserCategoryResource> {

    public UserCategoryResourceAssembler() {
        super(UserCategoryController.class, UserCategoryResource.class);
    }

    @Override
    public UserCategoryResource toResource(CueCategory category) {
        return new UserCategoryResource(category);
    }

}
