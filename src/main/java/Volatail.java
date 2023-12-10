import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Volatail {
    //    不加volatile 在main线程修改了之后不会马上写入到主内存导致不可见 程序会一直循环
    static volatile boolean flage;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("enter t1");
            while (flage) {

            }
            System.out.println("stop t1");
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flage = false;
        System.out.println("fix" + flage);
    }

    static int n = 0;

    @Test
    public void plus() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Thread tt = new Thread(() -> {
                    for (int j = 0; j < 100000; j++) {
                        n++;
                    }
                });
                tt.start();
                try {
                    tt.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            Object
//            ExecutorService
//            NewScheduledThreadPool

        });
        t1.start();
//        TimeUnit.SECONDS.sleep(10);
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(n);
    }

    static volatile long n1 = 0;

    @Test
    public void plus2() throws InterruptedException {
//        ReentrantLock
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                n1++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                n1++;
            }
        });
        t1.start();
        t2.start();
//        TimeUnit.SECONDS.sleep(10);
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(n1);
    }
}
