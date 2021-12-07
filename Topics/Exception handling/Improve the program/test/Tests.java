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

    final int[] IDS = new int[]{535, 99534533, 343455,};
    final String[] ITEMS = new String[]{"Chair", "Table", "Vase"};

    final int[] INCORRECT_IDS = new int[]{-100, -99, -2, -1, 0, 1, 2, 3, 99, 100, 101, 999, 1000,
            534, 536, 99534532, 99534534, 343454, 343456};

    @DynamicTest
    final DynamicTesting[] dt = new DynamicTesting[]{
            () -> testGetItems(IDS[0], ITEMS[0]),
            () -> testGetItems(IDS[1], ITEMS[1]),
            () -> testGetItems(IDS[2], ITEMS[2]),

            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[0]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[1]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[2]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[3]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[4]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[5]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[6]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[7]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[8]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[9]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[10]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[11]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[12]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[13]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[14]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[15]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[16]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[17]),
            () -> testGetItemsNotFoundStatusCode(INCORRECT_IDS[18]),
    };

    CheckResult testGetItems(int id, String item) {
        HttpResponse response = get("/items/" + id).send();

        throwIfIncorrectStatusCode(response, 200);

        if (!item.equals(response.getContent())) {
            return wrong("Expected: \"" + item + "\", received: \"" + response.getContent() + "\"");
        }

        return correct();
    }

    CheckResult testGetItemsNotFoundStatusCode(int id) {
        HttpResponse response = get("/items/" + id).send();

        throwIfIncorrectStatusCode(response, 404);

        return correct();
    }
}