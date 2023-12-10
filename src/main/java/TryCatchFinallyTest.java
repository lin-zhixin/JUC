import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class TryCatchFinallyTest {
    public static void main(String[] args) {
        System.out.println(func());
    }

    public static int func() {
//
//        ReentrantLock
        Integer a = 10;
        try {
            System.out.println("try中的代码块");
            a = a / 0;
            return a += 10;
        } catch (Exception e) {
            System.out.println("catch中的代码块");
            return a += 15;
        } finally {
            System.out.println("finally中的代码块");
            if (a > 10) {
                System.out.println("a > 10, a = " + a);
            }
            a += 50;
            System.out.println(a);
        }
    }
//————————————————
//    版权声明：本文为CSDN博主「星光_依旧灿烂」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_46804966/article/details/116138354
}
