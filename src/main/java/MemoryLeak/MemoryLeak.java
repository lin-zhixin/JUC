package MemoryLeak;

import java.util.ArrayList;
import java.util.List;

//1-静态集合类
//        静态集合类，如HashMap、LinkedList等等。如果这些容器为静态的，那么它们的生命周期与JVM程
//        序一致，则容器中的对象在程序结束之前将不能被释放，从而造成内存泄漏。简单而言，长生命周期的
//        对象持有短生命周期对象的引用，尽管短生命周期的对象不再使用，但是因为长生命周期对象持有它的
//        引用而导致不能被回收。
public class MemoryLeak {
    static List list = new ArrayList();

    public void oomTests() {
        Object obj = new Object();//局部变量
        list.add(obj);
    }
}
