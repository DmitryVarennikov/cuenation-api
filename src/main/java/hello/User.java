package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

public class User extends ResourceSupport {

    @Id
    private String id;

    private String token;

    @JsonCreator
    public User(@JsonProperty("token") String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
