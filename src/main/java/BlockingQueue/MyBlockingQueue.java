package BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

public class MyBlockingQueue {
    private static final Object LOCK = new Object();
    private static int state = 0;

    public static void producer() {
        synchronized (LOCK) {
            while (state == 1) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("p");
            LOCK.notifyAll();
        }
    }

    public static void consumer() {
        synchronized (LOCK) {
            while (state == 0) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            state=0;
            System.out.println("c");
            LOCK.notifyAll();
        }
    }



}
