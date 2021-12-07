package task;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@RestController
public class Controller {
    final ConcurrentHashMap<Long, String> items = new ConcurrentHashMap<>(Map.of(
            55L, "Chair",
            99L, "Table",
            345L, "Vase"
    ));

    final String ERROR = "Item wasn't found";

    @GetMapping("/items/{id}")
    String getItem(@PathVariable long id) {
        String item = items.get(id);
        return item != null ? item : ERROR;
    }

    // Add your code below this line and do not change the code above the line.
    @DeleteMapping("/items/{id}")
    String deleteItem(@PathVariable long id) {
        String item = items.remove(id);
        return item == null ? ERROR : item;
    }
}
