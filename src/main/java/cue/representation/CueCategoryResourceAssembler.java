package cue.representation;

import cue.CategoryController;
import cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CueCategoryResourceAssembler extends ResourceAssemblerSupport<CueCategory, CueCategoryResource> {

    public CueCategoryResourceAssembler() {
        super(CategoryController.class, CueCategoryResource.class);
    }

    @Override
    public CueCategoryResource toResource(CueCategory category) {
        return new CueCategoryResource(category);
    }

    public ResourceSupport getResponse(List<CueCategory> categories) {
        List<CueCategoryResource> categoryResources = toResources(categories);
        return Resources.wrap(categoryResources);
    }
}
