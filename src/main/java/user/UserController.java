package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user-tokens")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{token}")
    @ResponseBody
    public HttpEntity<UserResource> get(@PathVariable("token") String token) {
        UserEntity user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user instanceof UserEntity) {
            UserResource userResource = new UserResource(user);
            responseEntity = new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<UserResource>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserResource> post() {
        UUID uuid = UUID.randomUUID();

        UserEntity user = new UserEntity(uuid.toString());
        userRepository.save(user);

        return new ResponseEntity<UserResource>(HttpStatus.CREATED);
    }

}
