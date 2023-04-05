import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(() -> {
            synchronized (o1) {
                System.out.println("t1 get o1");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("t1 get o2");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t1 out o2");
            }
            System.out.println("t1 out o1");
        },"t1").start();
        new Thread(() -> {
            synchronized (o2) {
                System.out.println("t2 get o2");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("t2 get o1");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t2 out o1");
            }
            System.out.println("t2 out o2");
        },"t2").start();

    }
}
