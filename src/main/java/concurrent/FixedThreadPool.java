package concurrent;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//线程数固定的线程池
public class FixedThreadPool {

public static void main(String[] args) throws IOException, InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 4; i++) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread start");
                try {
                    Thread.sleep(new Random().nextInt(10 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        service.execute(run);
    }
    service.shutdown();
    service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    System.out.println("all thread complete");
}
}  