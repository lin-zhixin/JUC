package productAndComsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// 生产者消费者模型
public class PCModel {

    private List<Integer> queue = new ArrayList<Integer>();
    private int cap;
    private AtomicInteger i;
//    private Integer i;

    public void put() {
        int t = i.incrementAndGet();
        queue.add(t);
        System.out.println(Thread.currentThread().getName() + "add：" + t);
//        synchronized (queue) {
//            while (queue.size() >= cap) {
//                try {
//                    queue.wait();
//                    System.out.println(Thread.currentThread().getName() + "：满等");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
////            int t = i.incrementAndGet();
////            int t = (i++);
////            queue.add(t);
////            System.out.println(Thread.currentThread().getName() + "add：" + t);
//            queue.notifyAll();
//        }
    }

    public void get() throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == 0) {
                queue.wait();
                System.out.println(Thread.currentThread().getName() + "：空等");
            }
            System.out.println(Thread.currentThread().getName() + "delet：" + queue.remove(queue.size() - 1));
            queue.notifyAll();
        }
    }

    public static void main(String[] args) {
        PCModel pc = new PCModel();
        pc.cap = 5;
        pc.i = new AtomicInteger(0);
//        pc.i = 0;
//        for (int i = 0; i < 1000; i++) {
//            new Thread(() -> {
//                try {
//                    pc.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }, "C" + i).start();
//        }
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                pc.put();
            }, "P" + i).start();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
                    try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println(pc.i);

    }
}
