package hawk.service;

import hawk.entity.User;
import hawk.form.Search;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserSearchService {

    private static final Logger LOGGER = Logger.getLogger(UserSearchService.class.getName());

    EntityManager entityManager;
    @Autowired
    public UserSearchService(EntityManager entityManager){
        this.entityManager=entityManager;
    }


    public List<User> search(Search search) {
        final Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            List<User> users1 = new ArrayList<>();
            // The wrong way
            String query = "select id, name, description, tenant_id from public.usertb where name like '%" +
                    search.getSearchText() + "%'";

            LOGGER.log(Level.INFO, "SQL Query {0}",  query);
            ResultSet rs = connection
                    .createStatement()
                    .executeQuery(query);

            while (rs.next()) {
                users1.add(new User(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getString("tenant_id")));
            }
            rs.close();
            return users1;
        });
    }
}
