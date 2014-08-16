package cuenation.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@ComponentScan
@EnableMongoRepositories
@EnableSpringDataWebSupport
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class TestConfiguration {

}
