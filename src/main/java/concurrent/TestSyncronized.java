package concurrent;

import java.io.FileNotFoundException;

public class TestSyncronized {
static class MyThread extends Thread {
    int id;

    public MyThread(int id) {
        this.id = id;
    }
}

static Object object = new Object();
static Thread[] a = new Thread[10];
static int x = a.length - 1;

public static void main(String[] args) throws FileNotFoundException {
    System.out.println("weidiao");
    for (int i = 0; i < a.length; i++) {
        a[i] = new MyThread(i) {
            @Override
            public void run() {
                while (true) {
                    synchronized (object) {
                        if (x == id) {
                            x--;
                            System.out.println(x + " " + id);
                            break;
                        }
                    }
                }
            }
        };
        a[i].start();
    }
}
}
