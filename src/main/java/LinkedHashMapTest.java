import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedHashMapTest {

    public static void main(String[] args) {
//        构造器里面的accessOrder设置为true表示按照访问顺顺序，就是访问到的放在头部（尾部
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap(16, 0.75f, true);
        linkedHashMap.put("age", "2");
        linkedHashMap.put("name", "1");
        linkedHashMap.put("sex", "3");
        linkedHashMap.forEach((k, v) -> System.out.println(k + "：" + v));

        linkedHashMap.get("name");

        linkedHashMap.forEach((k, v) -> System.out.println(k + "：" + v));
    }
}
