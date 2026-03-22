package hawk.api.jwt;

import hawk.api.SearchResult;
import hawk.entity.Item;
import hawk.form.Search;
import hawk.repos.ItemsRepo;
import hawk.service.SearchService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jwt/items")
public class JwtItemController {


    ItemsRepo repo;

    private final SearchService searchService;

    @Autowired
    public JwtItemController(SearchService searchService, ItemsRepo repo) {

        this.searchService = searchService;
        this.repo=repo;
    }

    @GetMapping("/search/")
    public ResponseEntity<List<Item>> searchAll() {
        Search search = new Search("");
        return ResponseEntity.ok(searchService.search(search));
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<Item>> search(@PathVariable("text") String text) {
        Search search = new Search(text);
        return ResponseEntity.ok(searchService.search(search));
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResult> search(@RequestBody Search search) {
        SearchResult result = new SearchResult(search.getSearchText(), searchService.search(search));
        return ResponseEntity.ok(result);
    }

    // @PathVariable("id") String id should be types correctly as a Long. eg: @PathVariable("id") Long id
    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable("id") String id) {
        val item = repo.findById(Long.getLong(id));
        return ResponseEntity.ok(item);
    }
}
