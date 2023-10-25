import java.util.concurrent.*;

public class CyclicBarrierTest {

    public static void main(String[] args) {
//        maximumPoolSize数量要和下面的真正创建的线程数要一致 不然就会导致线程池里面新建的的线程马上进行await
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        CyclicBarrier c = new CyclicBarrier(2, () -> {
            System.out.println("end");
        });
//ClassLoader
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "阻塞次数" + finalI);
            });
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "阻塞次数"+ finalI);
            });

        }


        pool.shutdown();
    }
}
