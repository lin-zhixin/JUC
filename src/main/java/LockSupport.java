import java.util.concurrent.TimeUnit;

public class LockSupport {
    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("come in");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("come in agin");
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (lock) {
                lock.notify();//会把当前锁的代码块的所有内容执行完再通知唤醒 先wait再notify且配对使用
                System.out.println("notify");
            }
            System.out.println("notify2");
            System.out.println("notify2");
            System.out.println("notify2");
            System.out.println("notify2");

        }).start();
    }
}
