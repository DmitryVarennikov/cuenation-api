package user;

import cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import user.dao.UserRepository;
import user.domain.User;
import user.representation.UserCategoryResource;
import user.request.UserSubscribedCategoriesRequest;

import java.util.List;

@RestController
@RequestMapping("/user-tokens/{token}/categories")
public class UserCategoryController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserCategoryResource>> list(@PathVariable("token") String token) {
        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<CueCategory> categories = user.getCategories();
            responseEntity = new ResponseEntity<>(categories, HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public HttpEntity<List<UserCategoryResource>> put(
            @PathVariable("token") String token,
            @RequestBody UserSubscribedCategoriesRequest request) {

        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userRepository.saveUserCategories(user, request);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }

        return responseEntity;
    }

}
