package productAndComsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Test1 {
    List<Integer> q = new ArrayList<>();
    Integer size = 0;

    public void put(Integer val) {
        synchronized (q) {
            while (q.size() >= size) {
                try {
                    System.out.println("P wait");
                    q.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            q.add(val);
//            size++;
            System.out.println("P:" + val);
            q.notifyAll();
        }
    }

    public Integer get() {
        Integer res;
        synchronized (q) {
            while (q.size() <= 0) {
                try {
                    System.out.println("C wait");
                    q.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            res = q.remove(q.size() - 1);
            System.out.println("C:" + res);
            size--;
            q.notifyAll();
        }
        return res;
    }

    public static void main(String[] args) throws InterruptedException {
        Test1 test1 = new Test1();
        test1.size = 4;

        CyclicBarrier cyclicBar=new CyclicBarrier(3,()->{
            System.out.println("3 Round");
        });
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            int finalI = i;
            new Thread(() -> {
                try {
                    cyclicBar.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test1.put(finalI);
            }).start();
        }
        for (int i = 0; i < 11; i++) {
//            Thread.sleep(1000);
            new Thread(() -> {
                test1.get();
            }).start();
        }

    }
}
