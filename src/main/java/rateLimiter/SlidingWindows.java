package rateLimiter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// 滑动窗口 以一分钟为窗口单位
public class SlidingWindows {
    //    滑动精度
    private Integer subSize;
    private long threshold;
    private TreeMap<Long, Integer> win;

    public SlidingWindows(int subSize, long threshold) {
        this.subSize = subSize;
        this.threshold = threshold;
        win = new TreeMap<>();
        long now = System.currentTimeMillis();
//        设置子窗口
        for (int i = 0; i < 5; i++) {
            win.put((now -= subSize), 0);
        }
    }

    public synchronized boolean allowReq() {
        long now = System.currentTimeMillis();
        long start = now - 60000;
        long cnt = 0;
//        清除过期的子区间 同时统计窗口内的总请求数量
        Iterator<Map.Entry<Long, Integer>> it = win.entrySet().iterator();
        Map.Entry<Long, Integer> entry = null;
        while (it.hasNext()) {
            entry = it.next();
            if (entry.getKey() < start) {
                it.remove();
            } else {
                cnt += entry.getValue();
            }
        }
        disWin();
        if (cnt >= threshold) {
            System.out.println("限流！");
            return false;
        }

//        增加新的请求 同时更新新的子窗口
        if (win.size() == 0) {
//            如果窗口里没有子窗口 说明很久没有新的请求了直接按照自己当前的时间作为第一个子窗口
            win.put(now - (now % 10), 1);
        } else {

            Long tail = entry.getKey();
            System.out.println(win.size() + "s");
            System.out.println(tail + "t");
//            从最后一个字窗口一直增加新的子窗口 直到now时间在新的子窗口范围内
            while (now - tail > subSize) {
                win.put((long) (tail += subSize), 0);
            }
//            在最后一个子窗口的数量+1个请求
            win.put((long) tail, win.getOrDefault((long) tail, 0) + 1);
        }
        disWin();
        return true;
    }

    private void disWin() {
        AtomicInteger ind = new AtomicInteger();
        AtomicInteger sum = new AtomicInteger();
        win.forEach((k, v) -> {
            System.out.println("sub" + ind + "->" + new Date(k) + ":" + v);
            ind.getAndIncrement();
            sum.addAndGet(v);
        });
        System.out.println("sum: " + sum);
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindows slidingWindows = new SlidingWindows(10000, 5);
        for (int i = 0; i < 1000; i++) {
            if (!slidingWindows.allowReq()) {
                Thread.sleep(10000);
            } else {
                System.out.println("success!");
                Thread.sleep(5000);
            }
        }
    }


}
