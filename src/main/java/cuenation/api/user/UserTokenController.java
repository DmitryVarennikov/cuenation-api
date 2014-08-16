package cuenation.api.user;

import cuenation.api.user.domain.User;
import cuenation.api.user.persistence.UserRepository;
import cuenation.api.user.representation.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        HttpEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserResource userResource = new UserResource(user);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaTypes.HAL_JSON.toString());

            responseEntity = new ResponseEntity<>(userResource, headers, HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<UserResource> post() {
        UUID uuid = UUID.randomUUID();

        User user = new User(uuid.toString());
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(UserTokenController.class).get(user.getToken())).toUri());
        headers.add("Content-Type", MediaTypes.HAL_JSON.toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
