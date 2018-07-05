package concurrent;

import java.util.concurrent.*;

public class TestFuture {

public static void main(String[] args)
        throws InterruptedException, ExecutionException {
    System.out.println("start main thread");
    final ExecutorService exec = Executors.newFixedThreadPool(5);

    Callable<String> call = new Callable<String>() {
        public String call() throws Exception {
            System.out.println("  start new thread.");
            Thread.sleep(1000 * 5);
            System.out.println("  end new thread.");
            return "some value.";
        }
    };
    Future<String> task = exec.submit(call);
    Thread.sleep(1000 * 2);
    task.get(); // 阻塞，并待子线程结束，
    exec.shutdown();
    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    System.out.println("end main thread");
}

}
