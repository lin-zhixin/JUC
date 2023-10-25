import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class i {
    volatile Integer a = 0;
//AtomicInteger
//    public void plus1() throws InterruptedException {
//
//        new Thread(() -> {
//            for (int j = 0; j < 10000; j++) {
//                Integer t=a;
//                while (a!=t);
//
//                do {
//                    t=a;
//                }
//                while ()
//                System.out.println(Thread.currentThread().getName() + " " + a++);
//            }
//        },"t1").start();
//        new Thread(() -> {
//            for (int j = 0; j < 10000; j++) {
////                i++;
//                System.out.println(Thread.currentThread().getName() + " " + a++);
//            }
//        },"t2").start();
//        Thread.sleep(2000);
//        System.out.println(a);
//
//    }

    public static void main(String[] args) throws InterruptedException {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        i i=new i();
//        i.plus1();

//        list.add(null);
//        list.add(1);
//        System.out.println(list);

//        for (int i = 0; i < 10; i++) {
//            List<Integer> list = new ArrayList<>();
//            new Thread(() -> {
//                list.add(1);
//            }).start();
//            new Thread(() -> {
//                list.add(2);
//            }).start();
//            TimeUnit.SECONDS.sleep(1);
//            if ((!res.contains(list)) && res.add(list)) ;
//        }


//        System.out.println(res);
    }
}
