package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/user-token")
    @ResponseBody
    public HttpEntity<User> post() {
        String token = "123456789";
        User user = new User(token);
        userRepository.save(user);

        user.add(linkTo(methodOn(UserController.class).post()).withSelfRel());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
