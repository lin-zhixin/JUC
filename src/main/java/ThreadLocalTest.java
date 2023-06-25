import java.util.Random;

public class ThreadLocalTest {
    public static void main(String[] args) {
        House house=new House();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                int size=new Random().nextInt(5)+1;
                for (int j = 0; j < size; j++) {
                    house.saleByThreadLocal();
                }
                System.out.println(Thread.currentThread().getName() + ":"+house.n.get());
            },String.valueOf(i)).start();
        }

    }

}
class House{
    ThreadLocal<Integer> n= ThreadLocal.withInitial(()->0);

    public void saleByThreadLocal() {
        n.set(n.get()+1);
    }
}
