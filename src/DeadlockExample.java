public class DeadlockExample {

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Поток 1 захватил ресурс A");

                sleep();

                System.out.println("Поток 1 ждёт ресурс B");

                synchronized (resourceB) {
                    System.out.println("Поток 1 получил ресурс B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("Поток 2 захватил ресурс B");

                sleep();

                System.out.println("Поток 2 ждёт ресурс A");

                synchronized (resourceA) {
                    System.out.println("Поток 2 получил ресурс A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}