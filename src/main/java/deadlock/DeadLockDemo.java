package deadlock;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    static O1 o1 = new O1();
    static O2 o2 = new O2();
    private static final Object lock = new Object();

    //    public static void main(String[] args) {
//        Object o1 = new Object();
//        Object o2 = new Object();
//
//        new Thread(() -> {
//            synchronized (o1) {
//                System.out.println("t1 get o1");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o2) {
//                    System.out.println("t1 get o2");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                System.out.println("t1 out o2");
//            }
//            System.out.println("t1 out o1");
//        }, "t1").start();
//        new Thread(() -> {
//            synchronized (o2) {
//                System.out.println("t2 get o2");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o1) {
//                    System.out.println("t2 get o1");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                System.out.println("t2 out o1");
//            }
//            System.out.println("t2 out o2");
//        }, "t2").start();
//
//    }
//    解决方案1：固定加锁的顺序(针对锁顺序死锁) 破坏循环等待；
//    public static void main(String[] args) {
//        Object o1 = new Object();
//        Object o2 = new Object();
//
//        new Thread(() -> {
//            synchronized (o1) {
//                System.out.println("t1 get o1");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o2) {
//                    System.out.println("t1 get o2");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                System.out.println("t1 out o2");
//            }
//            System.out.println("t1 out o1");
//        }, "t1").start();
//
//        new Thread(() -> {
//            synchronized (o1) {
//                System.out.println("t2 get o1");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o2) {
//                    System.out.println("t2 get o2");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                System.out.println("t2 out o2");
//            }
//            System.out.println("t2 out o1");
//        }, "t2").start();
//
//    }
//    解决方案2：使用可重入锁 tryLock()； 破坏不剥夺条件 就是tryLock不成功就释放锁
    public static void main(String[] args) {
        ReentrantLock o1 = new ReentrantLock();
        ReentrantLock o2 = new ReentrantLock();

        new Thread(() -> {
            while (true) {
                if (o1.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " get o1");
                        if (o2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " get o2");
                                try {
                                    Thread.sleep(2000);
                                    break;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } finally {
                                o2.unlock();
                                System.out.println(Thread.currentThread().getName() + " out o2");
                            }
                        }
                    } finally {
                        o1.unlock();
                        System.out.println(Thread.currentThread().getName() + " out o1");
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (o2.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " get o2");
                        if (o1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " get o1");
                                try {
                                    Thread.sleep(2000);
                                    break;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } finally {
                                o1.unlock();
                                System.out.println(Thread.currentThread().getName() + " out o1");
                            }
                        }
                    } finally {
                        o2.unlock();
                        System.out.println(Thread.currentThread().getName() + " out o2");
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }, "t2").start();
    }

//    解决方案3：一次性全部申请 使用一次sync解决 破坏持有并请求条件
//    public static void main(String[] args) {
//
//
//        new Thread(() -> {
////            能申请到lock说明能够锁住唯一(因为是static 属于类对象的 所以只有一个)的lock.class对象，这样说明只有当前实例的对象能够操作当前类的所有变量 其他实例都因为申请不成功就被拒之门外
////             也可以使用synchronized (DeadLockDemo.class)
//            synchronized (lock) {
//                System.out.println("t1 get o1");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("t1 get o2");
////                System.out.println(o1);
//
//            }
//
//        }, "t1").start();
//
//        new Thread(() -> {
//            synchronized (lock) {
//                System.out.println("t2 get o1");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("t2 get o2");
////                System.out.println(o1);
//
//            }
////            o1.wait();
//
//        }, "t2").start();
//
//
//    }
}

class O1 extends ReentrantLock {
//    private final Node<K, V>[] initTable() {
//        Node<K, V>[] tab;
//        int sc;
//        while ((tab = table) == null || tab.length == 0) {
//            // b1
//            if ((sc = sizeCtl) < 0)
//                Thread.yield(); // lost initialization race; just spin
//            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
//                try {
////                    双重校验：防止在b1处停止的线程在别的线程初始化之后又开始进行了 if ((sc = sizeCtl) < 0) 里面的(sc = sizeCtl) 操作导致cas成功，进入到这个try里面，
////                    因此再次做if判断，if不成功会再次进行finally 里面的sizeCtl = sc;
////                    把 - 1 的sizeCtl 改回到原来的sc
//                    if ((tab = table) == null || tab.length == 0) {
//
//                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
//                        @SuppressWarnings("unchecked")
//                        Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
//                        table = tab = nt;
//                        sc = n - (n >>> 2);
//                    }
//                } finally {
//                    sizeCtl = sc;
//                }
//                break;
//            }
//        }
//        return tab;
//    }
}

class O2 extends ReentrantLock {

}
