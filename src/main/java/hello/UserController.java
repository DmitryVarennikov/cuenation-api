package hello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/user-tokens")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public HttpEntity<UserResource> get(@PathVariable("id") String id) {
        User user = userRepository.findOne(id);
        UserResource userResource = new UserResource(user);

        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<UserResource>> list() {
        List<User> users = userRepository.findAll();

        UserResourceAssembler userResourceAssembler = new UserResourceAssembler();
        List<UserResource> userResources = userResourceAssembler.toResources(users);

        return new ResponseEntity<List<UserResource>>(userResources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserResource> post() {
        UUID uuid = UUID.randomUUID();

        User user = new User(uuid.toString());
        userRepository.save(user);

        return new ResponseEntity<UserResource>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<UserResource> delete(@RequestParam(value = "id", required = true) String id) {
        userRepository.delete(id);

        return new ResponseEntity<UserResource>(HttpStatus.NO_CONTENT);
    }

}
