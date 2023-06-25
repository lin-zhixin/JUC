package singleLen;

public class HungrySingleLen {
    private  static final HungrySingleLen singleLen =new HungrySingleLen();//
    private HungrySingleLen(){// private 标识限制外部进行访问

    }

    public  static HungrySingleLen hungrySingleLen() {
//        饿汉模式：类加载的时候就创建好了 调用这个方法的时候直接返回
        return singleLen;
    }
}
