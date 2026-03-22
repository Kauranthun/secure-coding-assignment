package hawk;

import hawk.service.SearchService;
import hawk.service.UserSearchService;
import hawk.service.UserService;
import javax.persistence.EntityManager;
import hawk.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    @Bean
    public SearchService searchService(EntityManager entityManager){
        return new SearchService(entityManager);
    }
    @Bean
    public UserSearchService userSearchService(EntityManager entityManager) { return new UserSearchService(entityManager); }
    @Bean
    public UserService userService(EntityManager entityManager,UserRepo userRepo) { return new UserService(entityManager,userRepo); }

}
