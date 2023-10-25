package productAndComsumer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// 各种多线程打印 通信原理
public class PrintTest {
    private Object o = new Object();
    private boolean flage = false;

    public void print1() {
//        使用两个线程轮流打印字符串A和字符串B（A和B交替打印，各打印10次.)
        new Thread(() -> {
            synchronized (o) {

                for (int i = 0; i < 10; i++) {
                    while (flage) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    flage = true;
                    System.out.println(Thread.currentThread().getName() + ":A");
                    o.notifyAll();

                }
            }

        }, "t1").start();
        new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < 10; i++) {
                    while (!flage) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    flage = false;
                    System.out.println(Thread.currentThread().getName() + ":B");
                    o.notifyAll();
                }
            }

        }, "t2").start();

    }

    //    三个线程如何交替打印ABC循环100次的问题。
//    这是一个典型的多线程同步的问题，需要保证每个线程在打印字母之前，能够判断是否轮到自己执行，以及在打印字母之后，能够通知下一个线程执行。为了实现这一目标，博主讲介绍以下5种方法：
//
//    使用synchronized和wait/notify
//    使用ReentrantLock和Condition
//    使用AtomicInteger和CAS
//    使用Semaphore
//    使用CyclicBarrier

    //    使用synchronized和wait/notify
    private int a = 0;

    public void print2() {
        new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < 100; i++) {
                    while (a % 3 != 0) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": A");
                    a = (a + 1) % 3;
                    o.notifyAll();

                }
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < 100; i++) {
                    while (a % 3 != 1) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": B");
                    a = (a + 1) % 3;
                    o.notifyAll();

                }
            }
        }, "t2").start();
        new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < 100; i++) {
                    while (a % 3 != 2) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": C");
                    a = (a + 1) % 3;
                    o.notifyAll();
                }
            }
        }, "t3").start();

    }

    //    使用ReentrantLock和Condition
    private ReentrantLock rlock = new ReentrantLock();
    private Condition condition1 = rlock.newCondition();
    private Condition condition2 = rlock.newCondition();
    private Condition condition3 = rlock.newCondition();

    public void print3() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                rlock.lock();
                try {
                    while (a % 3 != 0) {
                        condition1.await();
                    }
                    System.out.println(Thread.currentThread().getName() + ": A");
                    a = (a + 1) % 3;
                    condition2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rlock.unlock();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                rlock.lock();
                try {
                    while (a % 3 != 1) {
                        condition2.await();
                    }
                    System.out.println(Thread.currentThread().getName() + ": B");
                    a = (a + 1) % 3;
                    condition3.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rlock.unlock();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                rlock.lock();
                try {
                    while (a % 3 != 2) {
                        condition3.await();
                    }
                    System.out.println(Thread.currentThread().getName() + ": C");
                    a = (a + 1) % 3;
                    condition1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("after condition1.signal()");
                    rlock.unlock();
                }
            }
        }, "t3").start();

    }

    //    使用AtomicInteger和CAS
    private AtomicInteger ai = new AtomicInteger(0);

    public void print4() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int t;
                while ((t = ai.get()) % 3 != 0) ;
                System.out.println(Thread.currentThread().getName() + ": A");
                ai.compareAndSet(t, (t + 1) % 3);
            }

        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int t;
                while ((t = ai.get()) % 3 != 1) ;
                System.out.println(Thread.currentThread().getName() + ": B");
                ai.compareAndSet(t, (t + 1) % 3);
            }

        }, "t2").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int t;
                while ((t = ai.get()) % 3 != 2) ;
                System.out.println(Thread.currentThread().getName() + ": C");
                ai.compareAndSet(t, (t + 1) % 3);
            }

        }, "t3").start();

    }

    //    使用Semaphore
    private Semaphore se1 = new Semaphore(1);
    private Semaphore se2 = new Semaphore(0);
    private Semaphore se3 = new Semaphore(0);

    public void print5() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    se1.acquire();
                    System.out.println(Thread.currentThread().getName() + ": A");
                    se2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    se2.acquire();
                    System.out.println(Thread.currentThread().getName() + ": B");
                    se3.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t2").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    se3.acquire();
                    System.out.println(Thread.currentThread().getName() + ": C");
                    se1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t3").start();

    }

    //    使用CyclicBarrier
    private static int state = 0;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        switch (state) {
            case 0:
                System.out.println("A");
                break;
            case 1:
                System.out.println("B");
                break;
            case 2:
                System.out.println("C");
                break;
        }
        state = (state + 1) % 3;

    });

    public void print6() {
        new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();

    }


    public static void main(String[] args) {
        PrintTest p = new PrintTest();
//        p.print1();
        p.print6();
    }

}
