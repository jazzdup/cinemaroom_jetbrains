package task;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
public class Controller {
//    List<String> list = Collections.synchronizedList(new ArrayList<>());
    private ConcurrentLinkedDeque<String> users = new ConcurrentLinkedDeque<>();

    @PostMapping("/users")
    public void postUsers(@RequestParam String name) {
        users.add(name);
    }

    @GetMapping("/users")
    public List<String> getUsers() {
        return users.stream().collect(Collectors.toList());
    }
}
