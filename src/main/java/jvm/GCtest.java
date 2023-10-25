package jvm;

import java.util.ArrayList;
//-Xms60m -Xmx60m -XX:SurvivorRatio=8
public class GCtest {
    //    JVM 常见命令使用测试类
    public static void main(String[] args) throws InterruptedException {
        ArrayList<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            byte[] arr = new byte[1024 * 100];//100KB
            list.add(arr);
            Thread.sleep(1200);
        }
    }
}
