package cuenation.api.cue.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import cuenation.api.user.domain.User;

import java.util.Date;

@Document(collection = "user_cues")
public class UserCue {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Cue cue;

    private Date createdAt;

    private Date viewedAt;

    @JsonCreator
    public UserCue(User user, Cue cue) {
        this.user = user;
        this.cue = cue;
        this.createdAt = cue.getCreatedAt();
    }

    public String getId() {
        return id;
    }

    public Cue getCue() {
        return cue;
    }

    public Date getViewedAt() {
        return viewedAt;
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
