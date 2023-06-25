package singleLen;

import java.util.Objects;

public class LazySingleLen {
    private volatile static LazySingleLen singleLen;//加了volatile关键字避免创建时指令重排导致的错误

    private LazySingleLen() {// private 标识限制外部进行访问
    }

    public static LazySingleLen hungrySingleLen() {
        //第一个if是为了减少性能开销，多个线程到这个if的时候直接返回，避免进入下面的锁
        if (Objects.isNull(singleLen)) {
            synchronized (LazySingleLen.class) {
                //第二个if是为了避免创建多个实例 多个线程下如果线程a已经创建了完了一个singleLen对象，线程b在线程a创建过程中第一个if为true，a创建完之后b进去了之后如果没有第二个if就又新建了一个对象
                if (Objects.isNull(singleLen)) {
                    //下面这句话可能发生指令重排，new过程总共分为三步：1.内存分配 2.初始化 3.指向地址
                    // 如果第三步重排到第二步前面，就会导致多线程的其他线程在第一个if (Objects.isNull(singleLen))的时候判定为false直接执行return导致错误
                    // 因此加入volatile设置指令屏障避免cpu优化指令重排，可以避免这样的错误
                    singleLen = new LazySingleLen();
                }
            }
        }
        return singleLen;
    }

    public static void main(String[] args) {

    }

}
