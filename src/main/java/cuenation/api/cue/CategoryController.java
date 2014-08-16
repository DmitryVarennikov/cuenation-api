package cuenation.api.cue;

import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.persistence.CueCategoryRepository;
import cuenation.api.cue.representation.CueCategoryResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cue-categories")
public class CategoryController {

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private CueCategoryResourceAssembler cueCategoryResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> list() {
        List<CueCategory> categories = cueCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        ResourceSupport response = cueCategoryResourceAssembler.getResponse(categories);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
