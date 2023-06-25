import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    //    static volatile boolean stop=false;
    static AtomicBoolean stop = new AtomicBoolean(false);

    //    public static void main(String[] args) {
//        new Thread(()->{
//            while (true) {
//                if (Objects.equals(stop.get(),true)) {
//                    System.out.println(Thread.currentThread().getName()+"stop->"+stop);
//                    break;
//                }
//                System.out.println(Thread.currentThread().getName() + "hello valatile");
//            }
//        },"t1").start();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Thread(()->{
//            stop.set(true);
//        },"t2").start();
//    }
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "stop");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "hello");
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(t1::interrupt, "t2").start();

    }
}
