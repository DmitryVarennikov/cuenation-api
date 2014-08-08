package cue;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class CueCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, CueCategoryResource> {

    public CueCategoryResourceAssembler() {
        super(CategoryController.class, CueCategoryResource.class);
    }

    @Override
    public CueCategoryResource toResource(CueCategory category) {
        return new CueCategoryResource(category);
    }
}
