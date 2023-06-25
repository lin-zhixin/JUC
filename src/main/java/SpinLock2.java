import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 自旋锁实现
 */
public class SpinLock2 {
    AtomicReference<Integer> integerAtomicReference = new AtomicReference<>();

    public void lock() {
        System.out.println(Thread.currentThread().getName() + "lock");
        while (!integerAtomicReference.compareAndSet(null, 123)) {

        }
    }

    public void unLock() {
        System.out.println(Thread.currentThread().getName() + "lock");
        while (!integerAtomicReference.compareAndSet(123, null)) {

        }
    }

    //    public void stampedLock() {
//        System.out.println(Thread.currentThread().getName() + "stampedlock");
//        while (!integerAtomicStampedReference.compareAndSet(null, 123,)) {
//
//        }
//    }
    @Test
    public void stampedTest() {

        AtomicStampedReference<String> integerAtomicStampedReference = new AtomicStampedReference<>("123", 1);

//        String
        System.out.println(integerAtomicStampedReference.compareAndSet("123", "789", integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp()+1) + ":" + integerAtomicStampedReference.getReference() + " " + integerAtomicStampedReference.getStamp());
        System.out.println(integerAtomicStampedReference.compareAndSet("789", "123", integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp()+1) + ":" + integerAtomicStampedReference.getReference() + " "  + integerAtomicStampedReference.getStamp());
        System.out.println(integerAtomicStampedReference.compareAndSet("123", "456", integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp()+1) + ":" + integerAtomicStampedReference.getReference() + " " + integerAtomicStampedReference.getStamp());

    }

//    public void stampedUnLock() {
//        System.out.println(Thread.currentThread().getName() + "stampedunlock");
//        while (!integerAtomicStampedReference.compareAndSet(123, null)) {
//
//        }
//    }

    public static void main(String[] args) {
        SpinLock2 spinLock2 = new SpinLock2();
        new Thread(() -> {
            spinLock2.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock2.unLock();
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLock2.lock();
//            try {
////                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            spinLock2.unLock();
        }).start();

    }
    
}
