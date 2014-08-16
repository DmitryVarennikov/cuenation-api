package cuenation.api.user;

import cuenation.api.AbstractContextControllerTests;
import cuenation.api.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserTokenControllerTest extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        operations.dropCollection(User.class);
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void createAndCheckUserToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user-tokens").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(header().string("Location", containsString("http://localhost/user-tokens/")))
                .andReturn();

        String locationHeader = mvcResult.getResponse().getHeader("Location");
        String token = pullOutTokenFromUrl(locationHeader);

        mockMvc.perform(get("/user-tokens/{token}", token).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._links.self.href", anything()))
                .andExpect(jsonPath("$._links.userCueCategories.href", anything()))
                .andExpect(jsonPath("$._links.userCues.href", anything()))
                .andExpect(jsonPath("$.token", is(token)))
        ;
    }
}