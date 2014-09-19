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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public HttpEntity<ResourceSupport> list(@RequestHeader(value = "If-None-Match", required = false) String ifNonMatch) {
        ResponseEntity<ResourceSupport> response;

        List<CueCategory> categories = cueCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        ResourceSupport responseBody = cueCategoryResourceAssembler.getResponse(categories);


        String eTag = "";
        try {
            byte[] responseBytes = MessageDigest.getInstance("MD5").digest(responseBody.toString().getBytes("UTF-8"));
            BigInteger number = new BigInteger(1, responseBytes);
            eTag = number.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("ETag", eTag);
        if (eTag.equals(ifNonMatch)) {
            response = new ResponseEntity<>(headers, HttpStatus.NOT_MODIFIED);
        } else {
            response = new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }

        return response;
    }

}
