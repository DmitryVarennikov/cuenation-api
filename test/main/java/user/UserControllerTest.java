package user;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UserControllerTest {

    private MockMvc mockMVC;

    private UserController userController;

    @Before
    public void setUp() {

    }

    @Test
    @Ignore
    public void post() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        RestTemplate template = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

//        ResponseEntity<UserController> responseEntity = template.getForEntity(
//                "http://localhost:8080/user-tokens/non-existing-token", UserController.class);

//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}