package hawk;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.logging.Logger;
import hawk.context.TenantContext;
import hawk.entity.Item;
import hawk.entity.User;
import hawk.repos.ItemRepo;
import hawk.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    Logger logger = Logger.getLogger(getClass().getName());

    private static final String ITEM = "item: %s";
    private static final String NUMBER_SUITE = "1234567";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, ItemRepo repo, UserRepo userRepo) {


        return args -> {

            logger.info("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.info(beanName);
            }


            logger.info(String.format("Load some fixture data %s", dbUrl));

            logger.info(String.format("Items in DB %d", repo.count()));

            if (repo.count() == 0) {
                repo.findAll().forEach(item -> logger.info(String.format(ITEM, item.getName())));

                Stream.of(1, 2, 3).forEach(i -> {
                    logger.info(String.format("Adding item%d", i));
                    repo.save(new Item(String.format("item%d", i), String.format("we have the best items, item%d", i)));
                });

                logger.info(String.format("Items in DB %d", repo.count()));
                repo.findAll().forEach(item -> logger.info(String.format(ITEM, item.getName())));
            }

            logger.info(String.format("Users in DB %d", userRepo.count()));

            if (userRepo.count() == 0) {
                userRepo.findAll().forEach(item -> logger.info(String.format(ITEM, item.getName())));

                TenantContext.setCurrentTenant(NUMBER_SUITE);
                Stream.of(1, 2, 3).forEach(i -> {
                    logger.info(String.format("Adding user%d", i));
                    userRepo.save(new User(String.format("user%d", i), String.format("we have the best users, users%d", i), NUMBER_suite));
                });

                // This should be removed once we confirm that all instances of "user" have been removed
                userRepo.save(new User("user", "The auth user", NUMBER_SUITE));

                userRepo.save(new User("janesmith", "The auth user", NUMBER_SUITE));

                TenantContext.setCurrentTenant("12345678");
                Stream.of(4, 5, 6).forEach(i -> {
                    logger.info(String.format("Adding item%d", i));
                    userRepo.save(new User(String.format("user%d", i), String.format("we have the best users, users%d", i), "12345678"));
                });


                logger.info(String.format("Users in DB %d", userRepo.count()));
                userRepo.findAll().forEach(item -> logger.info(String.format("user: %s", item.getName())));
            }

        };
    }


}
