package user.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserSubscribedCategoriesRequest {

    @JsonProperty("ids")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    @Override
    public String toString() {
        return "UserCategoryBodyRequest{" +
                "ids=" + ids +
                '}';
    }
}
