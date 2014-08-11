package user;

import cue.dao.UserCueRepository;
import cue.domain.UserCue;
import cue.service.CueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
import user.representation.UserCueResourceAssembler;
import user.request.UserViewedCuesRequest;
import user.service.UserCueService;

@RestController
@RequestMapping("/user-tokens/{token}/cues")
public class UserCueController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCueRepository userCueRepository;

    @Autowired
    private CueService cueService;

    @Autowired
    private UserCueService userCueService;

    @Autowired
    private UserCueResourceAssembler userCueResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<UserCue>> list(@PathVariable("token")
                                                    String token,
                                                    @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                                                    Pageable pageable,
                                                    PagedResourcesAssembler assembler) {
        User user = userRepository.findByToken(token);

        ResponseEntity<PagedResources<UserCue>> responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // first find out if user has new cues
            cueService.updateUserCues(user);


            Page<UserCue> userCues = userCueRepository.findAllByViewedAtExists(false, pageable);
            responseEntity = new ResponseEntity<PagedResources<UserCue>>(
                    assembler.toResource(userCues, userCueResourceAssembler), HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public HttpEntity put(
            @PathVariable("token") String token,
            @RequestBody UserViewedCuesRequest request) {

        User user = userRepository.findByToken(token);

        ResponseEntity responseEntity;
        if (user == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userCueService.markCuesAsViewed(user, request);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }

        return responseEntity;
    }

}
