package task;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @PostMapping("/api/products")
    Product products(@RequestBody Product product){
        return product;
    }
}
