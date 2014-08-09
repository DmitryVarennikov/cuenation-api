package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dao.UserRepository;
import user.domain.User;
import user.representation.UserResource;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/user-tokens")
public class UserTokenController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{token}")
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
    public ResponseEntity<UserResource> post() {
        UUID uuid = UUID.randomUUID();

        User user = new User(uuid.toString());
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UserTokenController.class).get(user.getToken())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
