package thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Status {
//    public static void main(String[] args) {
//        Thread t=new Thread(){
//            public void run() {
//                log.debug("run");
//                try {
//                    Thread.sleep(20000);
//                } catch (InterruptedException e) {
//                    log.error("wake up");
//                    e.printStackTrace();
//                }
//            }
//        };
//        System.out.println(t.getState());
//        t.start();
//        System.out.println(t.getState());
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t.interrupt();
//        System.out.println(t.getState());
//        System.out.println(t.getState());
//        System.out.println(t.getState());
//    }

    /**
     * 优先级 yiled
     */
    @Test
    public void status(){
        Runnable r1=()->{
            int count=0;
            while (true) {
                System.out.println("-->r1:"+count++);
//                log.info("-->r1:{}",count++);
            }
        };
        Runnable r2=()->{
            int count=0;
            while (true) {
//                Thread.yield();
                System.out.println("----------->r2:"+count++);
//                log.info("----->r2:{}",count++);
            }
        };
        Thread t1=new Thread(r1,"r1");
        Thread t2=new Thread(r2,"r2");
        t1.start();
        t2.start();
        t1.setPriority(Thread.MAX_PRIORITY);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void join(){
        AtomicInteger n= new AtomicInteger();
        Runnable r1=()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            n.set(5);
        };
        Thread t1=new Thread(r1,"r1");
        t1.start();
        try {
//            t1需要5秒 这边只等2秒 所以等t1结束就直接下面的操作
            TimeUnit.SECONDS.timedJoin(t1,2);
//            t1.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(n);

    }

    /**
     * 两阶段终止模式实现 r1打断r2
     */
    @Test
    public void interrupt() throws InterruptedException {
        Runnable r1 = () -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    Thread.currentThread().interrupt();
                }

            }
        };
        Thread t=new Thread(r1);
        t.start();

        System.out.println("main end");
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t.interrupt();
//        t.join();




    }



}
