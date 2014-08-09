package cue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import user.User;

import java.util.Date;

@Document(collection = "user_cues")
public class UserCue {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Cue cue;

    private Date viewedAt;

    public UserCue(User user, Cue cue) {
        this.user = user;
        this.cue = cue;
    }

    public String getId() {
        return id;
    }
}
