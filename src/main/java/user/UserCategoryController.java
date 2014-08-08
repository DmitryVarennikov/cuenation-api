package user;

import cue.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

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
    public ResponseEntity<List<UserCategoryResource>> put(
            @PathVariable("token") String token,
            @RequestBody UserCategoryBodyRequest request) {

        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.setUserCategories(user, request);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }

        return responseEntity;
    }

}