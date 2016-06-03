package cuenation.api.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cuenation.api.AbstractContextControllerTests;
import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.domain.UserCue;
import cuenation.api.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserCueControllerTest extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        operations.dropCollection(User.class);
        operations.dropCollection(CueCategory.class);
        operations.dropCollection(Cue.class);
        operations.dropCollection(UserCue.class);
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/user-tokens/{token}/cues", "non-existing-token")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void noUserCues() throws Exception {
        String token = createUser(mockMvc);

        mockMvc.perform(get("/user-tokens/{token}/cues", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                        // @TODO: must be "application/hal+json"
//                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.page.totalElements", equalTo(0)))
                .andExpect(jsonPath("$.page.totalPages", equalTo(0)))
                .andExpect(jsonPath("$.page.number", equalTo(0)))
        ;
    }

    @Test
    public void createAndGetUserCues() throws Exception {
        String token = createUser(mockMvc);

        List<CueCategory> categories = savePreparedCategories();
        subscribeForCategories(token, categories);
        // implies bg task processed by the remote sever
        savePreparedCues(categories);


        // fetch 2 cues we subscribed on through categories
        MvcResult cuesResult = mockMvc.perform(get("/user-tokens/{token}/cues", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", equalTo(2)))
                .andExpect(jsonPath("$.page.totalPages", equalTo(1)))
                .andExpect(jsonPath("$.page.number", equalTo(0)))
                        // also pay attention we can not verify cue IDs as coming IDs are actually belong UserCue
                        // objects, not Cue objects
                .andExpect(jsonPath("$._embedded.userCues", hasSize(2)))
                .andExpect(jsonPath("$._embedded.userCues[0].title", equalTo("title1")))
                .andExpect(jsonPath("$._embedded.userCues[0].link", equalTo("link1")))
                .andExpect(jsonPath("$._embedded.userCues[0].categoryId", equalTo(categories.get(0).getId())))
                .andExpect(jsonPath("$._embedded.userCues[1].title", equalTo("title2")))
                .andExpect(jsonPath("$._embedded.userCues[1].link", equalTo("link2")))
                .andExpect(jsonPath("$._embedded.userCues[1].categoryId", equalTo(categories.get(1).getId())))
                .andReturn();

        String content = cuesResult.getResponse().getContentAsString();
        UserCueController.PuRequest request = prepareRequestToSetCueAsViewed(content);

        // now let's mark one cue as viewed
        mockMvc.perform(put("/user-tokens/{token}/cues", token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
        ;


        // then grab user cues again and make sure now it returns just one cue
        mockMvc.perform(get("/user-tokens/{token}/cues", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", equalTo(1)))
                .andExpect(jsonPath("$.page.totalPages", equalTo(1)))
                .andExpect(jsonPath("$.page.number", equalTo(0)))
                        // also pay attention we can not verify cue IDs as coming IDs are actually belong UserCue
                        // objects, not Cue objects
                .andExpect(jsonPath("$._embedded.userCues", hasSize(1)))
        ;
    }

    private List<CueCategory> savePreparedCategories() {
        CueCategory category1 = new CueCategory("name1", "link1");
        CueCategory category2 = new CueCategory("name2", "link2");

        operations.save(category1);
        operations.save(category2);

        return Arrays.asList(category1, category2);
    }

    private void subscribeForCategories(String token, List<CueCategory> categories) throws Exception {
        List<String> ids = Arrays.asList(categories.get(0).getId(), categories.get(1).getId());
        UserCueCategoryController.PutRequest request = new UserCueCategoryController.PutRequest();
        ReflectionTestUtils.setField(request, "ids", ids);

        mockMvc.perform(put("/user-tokens/{token}/cue-categories", token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    private void savePreparedCues(List<CueCategory> categories) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date createdAt1 = calendar.getTime();

        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -4);
        Date createdAt2 = calendar.getTime();

        Cue cue1 = new Cue("title1", "link1", createdAt2, categories.get(0));
        Cue cue2 = new Cue("title2", "link2", createdAt1, categories.get(1));

        operations.save(cue1);
        operations.save(cue2);
    }

    private UserCueController.PuRequest prepareRequestToSetCueAsViewed(String jsonData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonData);
        Iterator<JsonNode> elements = rootNode.path("_embedded").path("userCues").elements();

        String firstId = null;
        while (elements.hasNext()) {
            JsonNode cue = elements.next();
            firstId = String.valueOf(cue.path("id").asText());
            break;
        }

        UserCueController.PuRequest request = new UserCueController.PuRequest();
        ReflectionTestUtils.setField(request, "ids", Arrays.asList(firstId));

        return request;
    }

}