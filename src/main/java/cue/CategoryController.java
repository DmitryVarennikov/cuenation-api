package cue;

import cue.dao.CueCategoryRepository;
import cue.domain.CueCategory;
import cue.representation.CueCategoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/cue-categories")
public class CategoryController {

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CueCategoryResource>> list() {
        List<CueCategoryResource> resources;
        List<CueCategory> categories = cueCategoryRepository.findAll();

        if (categories.isEmpty()) {
            resources = Collections.EMPTY_LIST;
        } else {
            resources = new LinkedList<>();
            for (CueCategory category : categories) {
                resources.add(new CueCategoryResource(category));
            }
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
