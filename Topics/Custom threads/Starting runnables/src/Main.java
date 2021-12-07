import java.util.Arrays;

class Starter {

    public static void startRunnables(Runnable[] runnables) {
        // implement the method
        Arrays.stream(runnables).forEach(r -> {
            Thread t = new Thread(r);
            t.start();
        });
    }
}