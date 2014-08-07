package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/user-tokens")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{token}")
    @ResponseBody
    public HttpEntity<UserResource> get(@PathVariable("token") String token) {
        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user instanceof User) {
            UserResource userResource = new UserResource(user);

            responseEntity = new ResponseEntity<>(userResource, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserResource> post() {
        UUID uuid = UUID.randomUUID();

        User user = new User(uuid.toString());
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UserController.class).get(user.getToken())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
