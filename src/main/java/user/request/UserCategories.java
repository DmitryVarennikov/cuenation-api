package user.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserCategories {

    @JsonProperty("category_ids")
    private List<String> categoryIds;

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    @Override
    public String toString() {
        return "UserCategoryBodyRequest{" +
                "categoryIds=" + categoryIds +
                '}';
    }
}
