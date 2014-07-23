package api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String token;

    public UserEntity(String token) {
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

}
