package rateLimiter;

import thread.Sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixedWindow {
    //    窗口内请求数量
    private long size;
    //    阈值
    private long threshold;
    //    窗口起始时间
    private long lastTime;
    //    窗口大小
    private long winLength;

    public FixedWindow(long threshold, long winLength) {
        this.threshold = threshold;
        this.winLength = winLength;
        this.size = 0;
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized boolean allowReq() {


        long now = System.currentTimeMillis();
        if (now - lastTime > winLength) {
            size = 0;
            lastTime = now;
        }
        if (size < threshold) {
            size++;
            return true;
        } else {
            return false;
        }


    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
//        for (Integer integer : list) {
//            list.remove(integer);
//        }

        Iterator<Integer> iterator = list.iterator();
//        System.out.println(iterator.);
        while (iterator.hasNext()) {
            System.out.println(546);
//            Integer t = iterator.next();
//            System.out.println(t);
//            list.add(100);
//            iterator.remove();
        }

    }


}
