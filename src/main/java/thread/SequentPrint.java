package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SequentPrint {

    Integer i = 0;
    Object o = new Object();

    //    wait notify
    public void p1() {
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                synchronized (o) {
                    while (!(i % 3 == 0)) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " A");
                    i = (i + 1) % 3;
                    o.notifyAll();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                synchronized (o) {
                    while (!(i % 3 == 1)) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " B");
                    i = (i + 1) % 3;
                    o.notifyAll();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                synchronized (o) {
                    while (!(i % 3 == 2)) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " C");
                    i = (i + 1) % 3;
                    o.notifyAll();
                }
            }
        }, "t3").start();
    }

    //    reentrainlock
    public void p2() {
        ReentrantLock lock = new ReentrantLock();
        Condition ca = lock.newCondition();
        Condition cb = lock.newCondition();
        Condition cc = lock.newCondition();
        new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                lock.lock();
                try {
                    while (i % 3 != 0) {
                        ca.await();
                    }
                    System.out.println(Thread.currentThread().getName() + " a");
                    i = (i + 1) % 3;
                    cb.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                lock.lock();
                try {
                    while (i % 3 != 1) {
                        cb.await();
                    }
                    System.out.println(Thread.currentThread().getName() + " b");
                    i = (i + 1) % 3;
                    cc.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                lock.lock();
                try {
                    while (i % 3 != 2) {
                        cc.await();
                    }
                    System.out.println(Thread.currentThread().getName() + " c");
                    i = (i + 1) % 3;
                    ca.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "t3").start();

    }

    //    cas
    AtomicInteger ai = new AtomicInteger(0);

    public void p3() {
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

    //    cyclicbarry
    public void p4() {
        CyclicBarrier cb = new CyclicBarrier(3, () -> {
            System.out.println("CyclicBarrier");
        });

        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                while (cb.getNumberWaiting() != 0) ;
                System.out.println(Thread.currentThread().getName() + "   a");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                while (cb.getNumberWaiting() != 1) ;
                System.out.println(Thread.currentThread().getName() + "   b");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                while (cb.getNumberWaiting() != 2) ;
                System.out.println(Thread.currentThread().getName() + "   c");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();

    }


    //    semaphorn
    public void p5() {
        Semaphore semaphorea = new Semaphore(1);
        Semaphore semaphoreb = new Semaphore(0);
        Semaphore semaphorec = new Semaphore(0);

        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                try {
                    semaphorea.acquire();
                    System.out.println(Thread.currentThread().getName() + "   a");
                    semaphoreb.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                try {
                    semaphoreb.acquire();
                    System.out.println(Thread.currentThread().getName() + "   b");
                    semaphorec.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
        new Thread(() -> {
            for (int j = 0; j < 5; j++) {
                try {
                    semaphorec.acquire();
                    System.out.println(Thread.currentThread().getName() + "   c");
                    semaphorea.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t3").start();

    }

    public static void main(String[] args) {
        SequentPrint se = new SequentPrint();
        se.p5();
    }
}
