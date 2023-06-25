import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁实现
 */
public class SpinLock {
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    public void lock() {
        AtomicInteger i=new AtomicInteger();
        i.getAndIncrement();

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "lock");
        while (!threadAtomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "unlock");
        while (!threadAtomicReference.compareAndSet(thread, null)) {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();

        new Thread(() -> {
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unLock();
        }).start();

        TimeUnit.MILLISECONDS.sleep(500);

        new Thread(() -> {
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unLock();
        }).start();


    }
}
