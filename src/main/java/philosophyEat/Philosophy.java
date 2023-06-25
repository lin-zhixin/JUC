package philosophyEat;

import sun.font.EAttribute;

import java.util.concurrent.TimeUnit;

//哲学家就餐问题解决
public class Philosophy extends Thread {
    Chopstick left;
    Chopstick right;

    public Philosophy(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
//        死锁例子：
//        while (true) {
//            synchronized (left) {
//                synchronized (right) {
//                    try {
//                        eat();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }

//        解决死锁 ：使用可重入锁里面的trylock实现两只筷子都能拿到的时候才吃饭 否则两只筷子都不拿（释放左手的筷子）
        while (true) {
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            try {
                                eat();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }

            }

        }
    }

    public void eat() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "eating");
        TimeUnit.SECONDS.sleep(1);
    }

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("c1");
        Chopstick c2 = new Chopstick("c2");
        Chopstick c3 = new Chopstick("c3");
        Chopstick c4 = new Chopstick("c4");
        Chopstick c5 = new Chopstick("c5");

        new Philosophy("p1", c1, c2).start();
        new Philosophy("p2", c2, c3).start();
        new Philosophy("p3", c3, c4).start();
        new Philosophy("p4", c4, c5).start();
        new Philosophy("p5", c5, c1).start();
    }
}
