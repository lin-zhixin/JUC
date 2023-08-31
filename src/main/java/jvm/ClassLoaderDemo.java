package jvm;

import java.lang.reflect.InvocationTargetException;

public class ClassLoaderDemo {
    //    自定义类加载器实现
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int base = 20000;

        MyClassLoader myClassLoader = new MyClassLoader("F:\\for_work\\my-project\\juctest\\target\\classes\\");
        System.out.println(cal(base,myClassLoader));


    }

    public static int cal(int base, ClassLoader classLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = classLoader.loadClass("jvm.Salary");
        Object salary = clazz.newInstance();
        int res = (int) clazz.getMethod("calculateYear", Integer.class).invoke(salary, base);
        System.out.println("总包：" + res);
        return res;

    }
}
