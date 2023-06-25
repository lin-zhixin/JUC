
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    public static void main(String[] args){

        CountDownLatch c=new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.countDown();
            System.out.println("不会阻塞");
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.countDown();
            System.out.println("不会阻塞");
        });
        t2.start();
        Thread t3 = new Thread(() -> {
            System.out.println("t3 start");
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3 end");
        });
        t3.start();

    }
}
