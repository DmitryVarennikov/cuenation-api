package hello;

import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.*;

public class Greeting extends ResourceSupport {

    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
