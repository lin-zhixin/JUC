import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupport3 {
    public static void main(String[] args) {
//        ConcurrentHashMap
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,3,1);
        Integer a = new Integer(3);
        Integer b = 3;
        System.out.println(a==b);
//        System.out.println(b.toString());
//        Lock lock= new ReentrantLock();
//        Thread t1 = new Thread(() -> {
//            System.out.println("come in");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            LockSupport.park();
//            System.out.println("come in2");
//        });
//        t1.start();
//        new Thread(() -> {
////            System.out.println("come in");
//            LockSupport.unpark(t1);
//            System.out.println("after unpark");
//        }).start();
//
//    }

    }
}
