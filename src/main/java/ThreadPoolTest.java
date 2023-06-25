import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService service1 = Executors.newCachedThreadPool();
        ExecutorService service2 = Executors.newFixedThreadPool(10);
        ExecutorService service3 = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
//            service1.execute(new MyTask(i));
            service2.execute(new MyTask(i));
            service2.shutdown();

//            service1.execute(new MyTask(i));
        }

    }
}

class MyTask implements Runnable {
    int i = 0;

//    AtomicInteger
    public MyTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "--" + i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}