import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    static O1 o1 = new O1();
    static O2 o2 = new O2();
    private static final Object lock=new Object();

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
//    public static void main(String[] args) {
//        O1 o1 = new O1();
//        O2 o2 = new O2();
//
//
//        new Thread(() -> {
//
//            while (o1.tryLock()) {
//                try {
//                    System.out.println("t1 get o1");
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (o2.tryLock()) {
//                        try {
//                            System.out.println("t1 get o2");
//                            try {
//                                TimeUnit.SECONDS.sleep(2);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        } finally {
//                            o2.unlock();
//                            System.out.println("t1 out o2");
//
//                        }
//
//                    }
//
//                } finally {
//                    o1.unlock();
//                    System.out.println("t1 out o1");
//
//                }
//            }
//        }, "t1").start();
//
//        new Thread(() -> {
//            try {
//                while (o1.tryLock(5,TimeUnit.SECONDS)) {
//                    try {
//                        System.out.println("t2 get o1");
//                        try {
//                            TimeUnit.SECONDS.sleep(2);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        if (o2.tryLock(5,TimeUnit.SECONDS)) {
//                            try {
//                                System.out.println("t2 get o2");
//                                try {
//                                    TimeUnit.SECONDS.sleep(2);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
//                                break;
//                            } finally {
//                                o2.unlock();
//                                System.out.println("t2 out o2");
//
//                            }
//
//                        }
//
//                    } finally {
//                        o1.unlock();
//                        System.out.println("t2 out o1");
//
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();
//
//
//    }
//    解决方案3：一次性全部申请 使用一次sync解决 破坏持有并请求条件
    public static void main(String[] args) {


        new Thread(() -> {
//            能申请到lock说明能够锁住唯一(因为是static 属于类对象的 所以只有一个)的lock.class对象，这样说明只有当前实例的对象能够操作当前类的所有变量 其他实例都因为申请不成功就被拒之门外
            synchronized (lock){
                System.out.println("t1 get o1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 get o2");
//                System.out.println(o1);

            }

        }, "t1").start();

        new Thread(() -> {
            synchronized (lock){
                System.out.println("t2 get o1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("t2 get o2");
//                System.out.println(o1);

            }
//            o1.wait();

        }, "t2").start();


    }
}

class O1 extends ReentrantLock {
}

class O2 extends ReentrantLock {

}
