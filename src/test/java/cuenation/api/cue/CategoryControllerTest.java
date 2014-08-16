package cuenation.api.cue;

import cuenation.api.AbstractContextControllerTests;
import cuenation.api.cue.domain.CueCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryControllerTest extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        operations.dropCollection(CueCategory.class);
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void getCategories() throws Exception {
        List<String> ids = savePreparedCategories();


        mockMvc.perform(get("/cue-categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.cueCategories", hasSize(2)))
                .andExpect(jsonPath("$._embedded.cueCategories[0].id", equalTo(ids.get(0))))
                .andExpect(jsonPath("$._embedded.cueCategories[0].name", equalTo("name1")))
                .andExpect(jsonPath("$._embedded.cueCategories[0].link", equalTo("link1")))
                .andExpect(jsonPath("$._embedded.cueCategories[1].id", equalTo(ids.get(1))))
                .andExpect(jsonPath("$._embedded.cueCategories[1].name", equalTo("name2")))
                .andExpect(jsonPath("$._embedded.cueCategories[1].link", equalTo("link2")))
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