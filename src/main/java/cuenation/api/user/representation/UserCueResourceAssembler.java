package cuenation.api.user.representation;

import cuenation.api.cue.domain.UserCue;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import cuenation.api.user.UserCueController;

@Component
public class UserCueResourceAssembler extends ResourceAssemblerSupport<UserCue, UserCueResource> {

    public UserCueResourceAssembler() {
        super(UserCueController.class, UserCueResource.class);
    }

    @Override
    public UserCueResource toResource(UserCue userCue) {
        return new UserCueResource(userCue);
    }

}
