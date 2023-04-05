import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目:谈谈你对多线程锁的理解，8锁案例说明
 * 口诀：线程操作资源类
 * 8锁案例说明：
 * 1标准访问有ab两个线程，请问先打印邮件还是短信 :email sms
 * 2 sendEmail方法中加入暂停3秒钟，请问先打印邮件还是短信 :email sms
 * 3添加一个普通的hello方法，请问先打印邮件还是hello :hello email
 * 4 有两部手机，请问先打印邮件还是短信 :sms email
 * 5有两个静态同步方法，有1部手机，请问先打印邮件还是短信 :email sms
 * 6有两个静态同步方法，有2部手机，请问先打印邮件还是短信 :email sms
 * 7有1个静态同步方法，有1个普通同步方法，有1部手机，谈问先打印邮件还是短信  :sms email
 * 8有1个静态同步方法，有1个普通同步方法，有2部手机，请问先打印邮件还是短信 :sms email
 */
//*笔记总结：
//        1-2
//        *
//        一个对象里面如果有多个synchronized方法，某一个时刻内，只要
//        一个线程去调用其中的一个synchronized方法了其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一的一个线程去访问这些synchronized方法锁的是当前对象this,被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
//        3-4
//        *加个普通方法后发现和同步锁无关
//        *换成两个对象后，不是同一把锁了，情况立刻变化。
//        5-6都换成静态同步方法后，情况又变化
//        三种 synchronized 锁的内容有一些差别：
//        对于普通同步方法，锁的是当前实例对象，通常指this，具体的一部部手机，所有的普通同步方法用的都是同一把锁—>实例对象本身，
//        对于静态同步方法，锁的是当前类的class对象，如Phone.class唯一的一个模板
//        对于同步方法块，锁的是 synchronized 括号内的对象
//        * *
//        7-8
//        当一个线程试图访问同步代码时它首先必须得到锁，正常退出或抛出异常时必须释放锁。
//        所有的普通同步方法用的都是同一把锁—实例对象本身，就是new出来的具体实例对象本身，本类this也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
//        所有的静态同步方法的也是同一把锁一类对象本身，就是我们说过的唯一模板cLaSs 一具体实例对象this和唯一模板CLass，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的
//        但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
//        */
public class EightLock {
    public static void main(String[] args) throws InterruptedException {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(200);

        new Thread(() -> {
            phone2.sendSMS();
        }).start();

        ReentrantLock lock = new ReentrantLock();

    }


}

class Phone {

    public static synchronized void sendEmail() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println("email");
    }

    public static synchronized void sendSMS() {
        System.out.println("sms");
    }

    public  void hello() {
        System.out.println("hello");
    }
}

