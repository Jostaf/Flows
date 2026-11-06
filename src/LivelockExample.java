import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class LivelockExample {

    public static void main(String[] args) {
        AtomicBoolean wants1 = new AtomicBoolean(true);
        AtomicBoolean wants2 = new AtomicBoolean(true);

        CyclicBarrier barrier = new CyclicBarrier(2);

        Runnable person1 = () -> {
            try {
                while (true) {
                    wants1.set(true);
                    barrier.await();

                    if (wants2.get()) {
                        System.out.println("Поток 1 уступает дорогу");
                        wants1.set(false);
                    }

                    barrier.await();
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable person2 = () -> {
            try {
                while (true) {
                    wants2.set(true);
                    barrier.await();

                    if (wants1.get()) {
                        System.out.println("Поток 2 уступает дорогу");
                        wants2.set(false);
                    }

                    barrier.await();
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(person1).start();
        new Thread(person2).start();
    }
}