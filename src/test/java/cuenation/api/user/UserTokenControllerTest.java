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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(header().string("Location", containsString("http://localhost/user-tokens/")))
                .andReturn();

        String locationHeader = mvcResult.getResponse().getHeader("Location");

        String regex = ".*([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(locationHeader);


        String token = "";
        if (matcher.matches() && matcher.groupCount() > 0) {
            token = matcher.group(1).trim();
        }

        if (0 == token.length()) {
            String message = String.format("Could not locate token in the \"Location\" header: [%s]", locationHeader);
            throw new RuntimeException(message);
        }

        mockMvc.perform(get("/user-tokens/{token}", token).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._links.self.href", anything()))
                .andExpect(jsonPath("$._links.userCueCategories.href", anything()))
                .andExpect(jsonPath("$._links.userCues.href", anything()))
                .andExpect(jsonPath("$.token", is(token)))
        ;
    }
}