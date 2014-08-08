package user;

import cue.CueCategory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, UserCategoryResource> {

    public UserCategoryResourceAssembler() {
        super(UserCategoryController.class, UserCategoryResource.class);
    }

    @Override
    public UserCategoryResource toResource(CueCategory category) {
        return new UserCategoryResource(category);
    }

}
