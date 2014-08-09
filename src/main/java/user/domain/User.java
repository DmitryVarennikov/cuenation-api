package user.domain;

import cue.domain.CueCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    @DBRef
    private List<CueCategory> categories;

    public User(String token) {
        setToken(token);
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CueCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<CueCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
