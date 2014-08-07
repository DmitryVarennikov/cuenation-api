package user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserControllerTest {

    private MockMvc mockMVC;

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void createUserToken() throws Exception {

        mockMVC.perform(post("/api-tokens/").accept(MediaType.APPLICATION_JSON));

//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");

//        Assert.assertTrue(false);

//        mockMVC.perform(post("/api-tokens/").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML));

        // @TODO: check for Location header that contains the link to the newly created resource


//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//
//        RestTemplate template = new RestTemplate();
//
//        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

//        ResponseEntity<UserController> responseEntity = template.getForEntity(
//                "http://localhost:8080/api-tokens/non-existing-token", UserController.class);

//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void userTokenExists() throws Exception {
        mockMVC.perform(get("/api-tokens/{token}", "5c611490-bda7-4c17-be2e-672a8c989f52")
                .accept(MediaType.APPLICATION_JSON));
    }

}