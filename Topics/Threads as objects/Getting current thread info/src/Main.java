class Info {

    public static void printCurrentThreadInfo() {
        // get the thread and print its info
        Thread thread = Thread.currentThread();
//        System.out.println(thread.toString());
        System.out.println("name: " + thread.getName());
        System.out.println("priority: " + thread.getPriority());
    }
}