package user.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User marks cues as viewed
 */
public class UserViewedCuesRequest {

    @JsonProperty("ids")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }
}
