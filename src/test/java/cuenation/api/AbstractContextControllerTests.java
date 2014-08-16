package cuenation.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
abstract public class AbstractContextControllerTests {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected MongoOperations operations;

    protected static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    protected String createUser(MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user-tokens").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
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

        return token;
    }

}
