import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupport2 {
    public static void main(String[] args) {
//        与synchronize里面的wait和notify一样 1，都需要在锁内 2.都需要先wait/await 再notify/signal
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();


        new Thread(() -> {
            lock.lock();

            System.out.println("come in");
            try {
                condition.await();
                System.out.println("wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();

            } finally {
                lock.unlock();
            }

        }).start();
    }
}
