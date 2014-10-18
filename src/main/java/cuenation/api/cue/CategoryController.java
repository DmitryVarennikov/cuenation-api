package cuenation.api.cue;

import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.persistence.CueCategoryRepository;
import cuenation.api.cue.representation.CueCategoryResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/cue-categories")
public class CategoryController {

    final private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private CueCategoryResourceAssembler cueCategoryResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> list(WebRequest request) {
        List<CueCategory> categories = cueCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        ResourceSupport responseBody = cueCategoryResourceAssembler.getResponse(categories);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public HttpEntity<ResourceSupport> list(WebRequest request) {
//        List<CueCategory> categories = cueCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
//        ResourceSupport responseBody = cueCategoryResourceAssembler.getResponse(categories);
//
//
//        String eTag = null;
//        try {
//            eTag = ETag.create(responseBody.toString());
//        } catch (ETag.Exception e) {
//            logger.error(e.toString());
//            e.printStackTrace();
//        }
//
//        if (request.checkNotModified(eTag)) {
//            return null;
//        } else {
//            return new ResponseEntity<>(responseBody, HttpStatus.OK);
//        }
//    }

}
