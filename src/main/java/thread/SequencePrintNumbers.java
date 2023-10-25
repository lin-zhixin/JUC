package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SequencePrintNumbers {

//    三个线程如何交替打印ABC循环100次的问题。
//    这是一个典型的多线程同步的问题，需要保证每个线程在打印字母之前，能够判断是否轮到自己执行，以及在打印字母之后，能够通知下一个线程执行。为了实现这一目标，博主讲介绍以下5种方法：
//
//    使用synchronized和wait/notify
//    使用ReentrantLock和Condition
//    使用AtomicInteger和CAS
//    使用Semaphore
//    使用CyclicBarrier

    public static int i = 0;
    public static Object lock = new Object();

    //    使用synchronized和wait/notify
    public void print1() {
//        交替打印012 循环100次
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    while (i % 3 != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    lock.notifyAll();
                }
            }
        }, "t0").start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    while (i % 3 != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    lock.notifyAll();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    while (i % 3 != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    lock.notifyAll();
                }
            }
        }, "t2").start();

    }

    public void print2() {
//        打印一奇一偶数
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    while (i % 2 != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + ": " + i++);
                    lock.notify();
                }
            }
        }, "t0").start();
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    while (i % 2 != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + ": " + i++);
                    lock.notify();
                }
            }
        }, "t1").start();
    }

    //    使用ReentrantLock和Condition
    private static final ReentrantLock relock = new ReentrantLock();
    private static final Condition a = relock.newCondition();
    private static final Condition b = relock.newCondition();
    private static final Condition c = relock.newCondition();

    public void print3() {

//        交替打印012 循环100次
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                relock.lock();
                try {
                    while (i % 3 != 0) {
                        a.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    b.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("after b.signal()");
                    relock.unlock();
                }
            }
        }, "t0").start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                relock.lock();
                try {
                    while (i % 3 != 1) {
                        b.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    c.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    relock.unlock();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                relock.lock();
                try {
                    while (i % 3 != 2) {
                        c.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    a.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    relock.unlock();
                }
            }
        }, "t2").start();


    }

    //    使用Semaphore
    private static final Semaphore semaphoreA = new Semaphore(1);
    private static final Semaphore semaphoreB = new Semaphore(0);
    private static final Semaphore semaphoreC = new Semaphore(0);

    public void print4() {
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    semaphoreA.acquire();
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    semaphoreB.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t0").start();
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    semaphoreB.acquire();
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    semaphoreC.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    semaphoreC.acquire();
                    System.out.println(Thread.currentThread().getName() + "：" + i);
                    i = (i + 1) % 3;
                    semaphoreA.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();


    }

    //    使用AtomicInteger和CAS
    private static AtomicInteger ai = new AtomicInteger(0);

    public void print5() {
        new Thread(() -> {
            for (int j = 0; j < 500; ) {
                if (ai.get() == 0) {
                    System.out.println(Thread.currentThread().getName() + " a");
                    ai.compareAndSet(0, 1);
                    j++;
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 500; ) {
                if (ai.get() == 1) {
                    System.out.println(Thread.currentThread().getName() + "  b");
                    ai.compareAndSet(1, 2);
                    j++;
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int j = 0; j < 500; ) {
                if (ai.get() == 2) {
                    System.out.println(Thread.currentThread().getName() + "   c");
                    ai.compareAndSet(2, 0);
                    j++;
                }
            }
        }, "t3").start();

    }


    public void print6() {

    }


    public static void main(String[] args) {
        SequencePrintNumbers se = new SequencePrintNumbers();
//        se.print1();
        se.print5();
    }
}
