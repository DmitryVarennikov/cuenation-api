package cuenation.api.cue.representation;

import cuenation.api.cue.CategoryController;
import cuenation.api.cue.domain.CueCategory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
        Resources resource = Resources.wrap(categoryResources);

        resource.add(ControllerLinkBuilder.linkTo(methodOn(CategoryController.class).list()).withSelfRel());

        return resource;
    }
}
