import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Singleton extends ReentrantLock {

    private static  Singleton singleton;
    private Integer a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public static Singleton getInstance() throws InterruptedException {
//        双重校验
        if (Objects.isNull(singleton)){
            synchronized (Singleton.class) {
                if (Objects.isNull(singleton)){
                    singleton=new Singleton();
                    singleton.setA(100);
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            new Thread(()->{

                try {
                    if (Objects.isNull(Singleton.getInstance())) {
                        System.out.println("error");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
