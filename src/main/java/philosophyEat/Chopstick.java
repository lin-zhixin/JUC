package philosophyEat;

import java.util.concurrent.locks.ReentrantLock;

//继承可重入锁来解决哲学家问题
public class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }
}
