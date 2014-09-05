package cuenation.api.cue.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import cuenation.api.user.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@TypeAlias("user_cue")
@Document(collection = "user_cues")
@CompoundIndexes({
        @CompoundIndex(def = "{'user': 1, 'category': 1}"),
        @CompoundIndex(def = "{'user': 1, 'viewedAt': 1, 'createdAt': -1}")
})
public class UserCue {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Cue cue;

    @DBRef
    // required for old cues deletion, see `UserCueRepository.removeByUserAndCategoryNotIn`
    private CueCategory category;

    // required for cues request sorting, see `UserCueController.list`
    private Date createdAt;

    // required for cues filtration upon request, see `UserCueRepository.findAllByUserAndViewedAtExists`
    private Date viewedAt;

    @JsonCreator
    public UserCue(User user, Cue cue) {
        this.user = user;
        this.cue = cue;
        this.category = cue.getCategory();
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

    @Override
    public String toString() {
        return "UserCue{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", cue=" + cue +
                ", createdAt=" + createdAt +
                ", viewedAt=" + viewedAt +
                '}';
    }
}
