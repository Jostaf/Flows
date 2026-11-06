public class AlternatingThreads {

    private static final Object lock = new Object();
    private static int turn = 1;

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turn != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.print("1");
                    turn = 2;
                    lock.notifyAll();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turn != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.print("2");
                    turn = 1;
                    lock.notifyAll();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}