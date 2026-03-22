package hawk.service;

import hawk.entity.Item;
import hawk.form.Search;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SearchService {

    private static final Logger LOGGER = Logger.getLogger(SearchService.class.getName());

    EntityManager entityManager;

    @Autowired
    public SearchService(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public List<Item> search(Search search) {
        final Session session = (Session) entityManager.unwrap(Session.class);
        return session.doReturningWork(new ReturningWork<List<Item>>() {
            @Override
            public List<Item> execute(Connection connection) throws SQLException {
                List<Item> items = new ArrayList<>();
                // The wrong way
                String query = "select id, name, description from ITEM where description like '%" +
                        search.getSearchText() + "%'";

                LOGGER.log(Level.INFO, "SQL Query: {0}",  query);
                try (java.sql.Statement statement = connection.createStatement();
                     ResultSet rs = statement.executeQuery(query)) {

                    while (rs.next()) {
                        items.add(new Item(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("description")
                        ));
                    }
                }

                return items;
            }
        });

    }


}
