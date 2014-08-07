package cue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cue_categories")
public class CueCategory {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public CueCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CueCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
