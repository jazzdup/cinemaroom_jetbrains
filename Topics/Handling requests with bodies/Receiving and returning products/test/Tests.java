import com.google.gson.Gson;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;

import org.hyperskill.hstest.testcase.CheckResult;
import task.Main;

import java.util.Random;

import static org.hyperskill.hstest.testcase.CheckResult.correct;


public class Tests extends SpringTest {

    public Tests() {
        super(Main.class);
    }

    static void throwIfIncorrectStatusCode(HttpResponse response, int status) {
        if (response.getStatusCode() != status) {
            throw new WrongAnswer(response.getRequest().getMethod() +
                    " " + response.getRequest().getLocalUri() +
                    " should respond with status code " + status +
                    ", responded: " + response.getStatusCode() + "\n\n" +
                    "Response body:\n" + response.getContent());
        }
    }

    static class Product {
        final String id;
        final String name;
        final String price;

        public Product(String id, String name, String price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    final Random rand = new Random();
    final Gson gson = new Gson();

    final Product[] PRODUCTS = new Product[]{
            new Product(Integer.toString(rand.nextInt()), "something " + rand.nextInt(), Integer.toString(rand.nextInt())),
            new Product(Integer.toString(rand.nextInt()), "something " + rand.nextInt(), Integer.toString(rand.nextInt())),
            new Product(Integer.toString( rand.nextInt()), "something " + rand.nextInt(), Integer.toString( rand.nextInt())),
    };

    final String[] JSON_PRODUCTS = new String[]{
            gson.toJson(PRODUCTS[0]),
            gson.toJson(PRODUCTS[1]),
            gson.toJson(PRODUCTS[2]),
    };


    @DynamicTest
    final DynamicTesting[] dt = new DynamicTesting[]{
            () -> testPostProduct(PRODUCTS[0], JSON_PRODUCTS[0]),
            () -> testPostProduct(PRODUCTS[1], JSON_PRODUCTS[1]),
            () -> testPostProduct(PRODUCTS[2], JSON_PRODUCTS[2]),
    };

    CheckResult testPostProduct(Product product, String jsonProduct) {
        HttpResponse response = post("/api/products", jsonProduct).send();

        throwIfIncorrectStatusCode(response, 200);

        expect(response.getContent()).asJson().check(
                isObject()
                        .value("id", isString(product.id))
                        .value("name", isString(product.name))
                        .value("price", isString(product.price)));

        return correct();
    }
}