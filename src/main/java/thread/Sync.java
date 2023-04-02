package thread;

import org.junit.Test;

import java.text.SimpleDateFormat;

public class Sync {
    public static int n = 0;

    public static void main(String[] args) {

    }

    @Test
    public void sync() throws InterruptedException {
        Thread t1=new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
//                System.out.println("enter t1");
                synchronized (Sync.class){
                    n++;
                }
//                System.out.println("t1->"+n);
            }
        }, "t1");
        Thread t2=new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
//                System.out.println("enter t2");
                synchronized (this){
                    n--;
                }

//                System.out.println("t2------->"+n);

            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(n);
//        SimpleDateFormat
    }
}
