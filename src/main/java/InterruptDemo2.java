import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo2 {
    //    static volatile boolean stop=false;
    static AtomicBoolean stop = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 300; i++) {
                System.out.println(i);
            }
        }, "t1");
        t1.start();
        Thread.interrupted();


    }
}
