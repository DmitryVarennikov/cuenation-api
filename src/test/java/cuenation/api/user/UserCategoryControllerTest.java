package cuenation.api.user;

import cuenation.api.AbstractContextControllerTests;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserCategoryControllerTest extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        operations.dropCollection(User.class);
        operations.dropCollection(CueCategory.class);
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/user-tokens/{token}/categories", "non-existing-token")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void userCategoriesNotSet() throws Exception {
        String token = createUser(mockMvc);

        mockMvc.perform(get("/user-tokens/{token}/categories", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void createAndGetUserCategories() throws Exception {
        String token = createUser(mockMvc);

        List<String> ids = savePreparedCategories();
        UserCategoryController.PutRequest request = new UserCategoryController.PutRequest();
        ReflectionTestUtils.setField(request, "ids", ids);


        mockMvc.perform(put("/user-tokens/{token}/categories", token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        mockMvc.perform(get("/user-tokens/{token}/categories", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                        // @TODO: must be "application/hal+json"
//                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.userCueCategories", hasSize(2)))
                .andExpect(jsonPath("$._embedded.userCueCategories[0].name", equalTo("name1")))
                .andExpect(jsonPath("$._embedded.userCueCategories[0].link", equalTo("link1")))
                .andExpect(jsonPath("$._embedded.userCueCategories[1].name", equalTo("name2")))
                .andExpect(jsonPath("$._embedded.userCueCategories[1].link", equalTo("link2")))
        ;
    }

    private List<String> savePreparedCategories() {
        CueCategory category1 = new CueCategory("name1", "link1");
        CueCategory category2 = new CueCategory("name2", "link2");

        operations.save(category1);
        operations.save(category2);

        return Arrays.asList(category1.getId(), category2.getId());
    }
}