package hawk.repos;

import hawk.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, Long> {

    List<Item> findByNameOrDescription(String name, String description);

}


