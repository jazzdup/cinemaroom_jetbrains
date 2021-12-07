import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;

import org.hyperskill.hstest.testcase.CheckResult;
import task.Main;

import static org.hyperskill.hstest.testcase.CheckResult.wrong;
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

    final String ITEMS = "/items/";
    final String NO_ITEM = "Item wasn't found";

    final int key1 = 55;
    final int key2 = 99;
    final int key3 = 345;

    final String val1 = "Chair";
    final String val2 = "Table";
    final String val3 = "Vase";

    final int fakeKey1 = 1000;
    final int fakeKey2 = 999;


    @DynamicTest
    DynamicTesting[] dt = new DynamicTesting[]{
            () -> testGetItem(key1, val1),
            () -> testDeleteItem(key1, val1),
            () -> testGetItem(key1, NO_ITEM),
            () -> testDeleteItem(key1, NO_ITEM),
            () -> testDeleteItem(key1, NO_ITEM),

            () -> testGetItem(key2, val2),
            () -> testDeleteItem(key2, val2),
            () -> testGetItem(key2, NO_ITEM),
            () -> testDeleteItem(key2, NO_ITEM),

            () -> testGetItem(key3, val3),
            () -> testDeleteItem(key3, val3),
            () -> testGetItem(key3, NO_ITEM),
            () -> testDeleteItem(key3, NO_ITEM),

            () -> testGetItem(fakeKey1, NO_ITEM),
            () -> testGetItem(fakeKey2, NO_ITEM),

            () -> testDeleteItem(fakeKey1, NO_ITEM),
            () -> testDeleteItem(fakeKey2, NO_ITEM)
    };


    CheckResult testGetItem(int key, String value) {
        HttpResponse response = get(ITEMS + key).send();

        throwIfIncorrectStatusCode(response, 200);

        if (!value.equals(response.getContent())) {
            return wrong("Expected: \"" + value + "\", received: \"" + response.getContent() + "\"");
        }

        return correct();
    }


    CheckResult testDeleteItem(int key, String value) {
        HttpResponse response = delete(ITEMS + key).send();

        throwIfIncorrectStatusCode(response, 200);

        if (!value.equals(response.getContent())) {
            return wrong("Expected: \"" + value + "\", received: \"" + response.getContent() + "\"");
        }

        return correct();
    }

}