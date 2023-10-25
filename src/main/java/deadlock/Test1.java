package deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
//    public static void main(String[] args) {
//        Object o1 = new Object();
//        Object o2 = new Object();
//        new Thread(() -> {
//            synchronized (o1) {
//                System.out.println(Thread.currentThread().getName() + "get o1");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o2) {
//                    System.out.println(Thread.currentThread().getName() + "get o2");
//                }
//            }
//        }, "t1").start();
//        new Thread(() -> {
//            synchronized (o2) {
//                System.out.println(Thread.currentThread().getName() + "get o2");
////                try {
//////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                synchronized (o1) {
//                    System.out.println(Thread.currentThread().getName() + "get o1");
//                }
//            }
//        }, "t2").start();
//    }

    //一次性申请所有
//    static Object o1 = new Object();
//    static Object o2 = new Object();
//
//    public static void main(String[] args) {
//
//        new Thread(() -> {
//            synchronized (Test1.class) {
//                System.out.println(Thread.currentThread().getName() + "get o1");
//                System.out.println(Thread.currentThread().getName() + "get o2");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t1").start();
//        new Thread(() -> {
//            synchronized (Test1.class) {
//                System.out.println(Thread.currentThread().getName() + "get o1");
//                System.out.println(Thread.currentThread().getName() + "get o2");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t2").start();
//
//    }
//


//    获取不到释放 破坏不剥夺
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


}
