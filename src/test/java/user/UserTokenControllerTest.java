package user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/test-applicationContext.xml"})
@WebAppConfiguration
public class UserTokenControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMVC;

    @Configuration
    public static class TestConfiguration {

        @Bean
        public UserTokenController simpleController() {
            return new UserTokenController();
        }

    }

    @Before
    public void setUp() {
        mockMVC = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        mockMVC = MockMvcBuilders.standaloneSetup(new UserTokenController()).build();
    }

    @Test
    public void createUserToken() throws Exception {

        // @TODO:try standalone http://blog.zenika.com/index.php?post/2013/01/15/spring-mvc-test-framework

        mockMVC.perform(post("/user-tokens/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

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

//        ResponseEntity<UserTokenController> responseEntity = template.getForEntity(
//                "http://localhost:8080/api-tokens/non-existing-token", UserTokenController.class);

//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

//    @Test
//    public void userTokenExists() throws Exception {
//        mockMVC.perform(get("/api-tokens/{token}", "5c611490-bda7-4c17-be2e-672a8c989f52")
//                .accept(MediaType.APPLICATION_JSON));
//    }

}