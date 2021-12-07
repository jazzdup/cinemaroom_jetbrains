import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;

import org.hyperskill.hstest.testcase.CheckResult;
import task.Main;

import java.util.List;
import java.util.Map;

import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;
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


    final List<String> data = List.of(
            "User123",
            "User999",
            "User555"
    );

    final String USERS = "/users";


    @DynamicTest
    DynamicTesting[] dt = new DynamicTesting[]{
            () -> testGetUsers(List.of()),
            () -> testPostUsers(data.get(0)),
            () -> testGetUsers(data.subList(0, 1)),
            () -> testPostUsers(data.get(1)),
            () -> testGetUsers(data.subList(0, 2)),
            () -> testPostUsers(data.get(2)),
            () -> testGetUsers(data)
    };

    CheckResult testPostUsers(String name) {
        HttpResponse response = post(USERS, Map.of("name", name)).send();

        throwIfIncorrectStatusCode(response, 200);

        return correct();
    }


    CheckResult testGetUsers(List<String> data) {
        HttpResponse response = get(USERS).send();

        throwIfIncorrectStatusCode(response, 200);

        var isArray = isArray();

        for (var user : data) {
            isArray.item(user);
        }

        expect(response.getContent()).asJson().check(
                isArray
        );

        return correct();
    }

}