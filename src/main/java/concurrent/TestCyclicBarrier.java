package concurrent;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCyclicBarrier {

static class Runner implements Runnable {

    private CyclicBarrier barrier;

    private String name;

    public Runner(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * (new Random()).nextInt(8));
            System.out.println(name + " 准备OK.");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " Go!!");
    }
}

public static void main(String[] args) throws IOException, InterruptedException {
    CyclicBarrier barrier = new CyclicBarrier(3);
    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.submit(new Runner(barrier, "zhangsan"));
    executor.submit(new Runner(barrier, "lisi"));
    executor.submit(new Runner(barrier, "wangwu"));
    executor.shutdown();
}

}