package cuenation.api.cue;

import cuenation.api.AbstractContextControllerTests;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.persistence.CueCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {CategoryControllerTest.TestConfiguration.class})
//public class CategoryControllerTest extends AbstractContextControllerTests {
//
//    @Autowired
//    @Qualifier("cueCategoryRepositoryMock")
//    private CueCategoryRepository cueCategoryRepository;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        Mockito.reset(cueCategoryRepository);
//        mockMvc = webAppContextSetup(this.wac).build();
//    }
//
//    @Test
//    public void getCategories() throws Exception {
//        List<CueCategory> categories = new LinkedList<>(Arrays.asList(new CueCategory("name1", "link1")));
//        when(cueCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"))).thenReturn(categories);
//
//
//        mockMvc.perform(get("/cue-categories"))
////                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaTypes.HAL_JSON))
//                .andExpect(jsonPath("$._embedded.cueCategories", hasSize(1)))
//                .andExpect(jsonPath("$._embedded.cueCategories[0].id", equalTo(null)))
//                .andExpect(jsonPath("$._embedded.cueCategories[0].name", equalTo("name1")))
//                .andExpect(jsonPath("$._embedded.cueCategories[0].link", equalTo("link1")))
//        ;
//    }
//
//    @Configuration
//    public static class TestConfiguration {
//
//        @Bean(name = "cueCategoryRepositoryMock")
//        @Primary
//        public CueCategoryRepository cueCategoryRepository() {
//            return Mockito.mock(CueCategoryRepository.class);
//        }
//
//    }
//}