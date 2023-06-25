import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(3,false);
        for (int i = 0; i < 10; i++) {

            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println("acquired");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    semaphore.release();
                }

            }).start();

        }
    }
}
