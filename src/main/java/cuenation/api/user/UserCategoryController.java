package cuenation.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.user.domain.User;
import cuenation.api.user.persistence.UserRepository;
import cuenation.api.user.representation.UserCategoryResource;
import cuenation.api.user.representation.UserCategoryResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-tokens/{token}/categories")
public class UserCategoryController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCategoryResourceAssembler userCategoryResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> list(@PathVariable("token") String token) {
        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<CueCategory> categories = user.getCategories();
            ResourceSupport response = userCategoryResourceAssembler.getResponse(user, categories);

            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public HttpEntity<List<UserCategoryResource>> put(
            @PathVariable("token") String token,
            @RequestBody PutRequest request) {
        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userRepository.saveUserCategories(user, request);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaTypes.HAL_JSON.toString());

            responseEntity = new ResponseEntity<>(headers, HttpStatus.OK);
        }

        return responseEntity;
    }

    public static class PutRequest {

        @JsonProperty("ids")
        private List<String> ids;

        public List<String> getIds() {
            return ids;
        }

        @Override
        public String toString() {
            return "PutRequest{" +
                    "ids=" + ids +
                    '}';
        }
    }

}
