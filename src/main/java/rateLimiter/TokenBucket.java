package rateLimiter;

import java.sql.Time;
//令牌桶算法
public class TokenBucket {
    //    桶容量
    private int cap;
    //    令牌数量
    private int size;
    //    一秒内令牌生成速率
    private int rate;
    //    最后一程生成令牌时间
    private long lastTime;

    public TokenBucket(int cap, int rate) {
        this.cap = cap;
        this.rate = rate;
        this.size = cap;
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized void resize() {
        long timeLength = System.currentTimeMillis() - lastTime;
        if (timeLength > 0) {
            size = (int) Math.min(size + (timeLength / 1000) * rate, cap);
            lastTime += timeLength;
        }
    }

    public boolean allowReq() {
        resize();
        if (size > 0) {
            size--;
            return true;
        } else {
            System.out.println("限流！！");
            return false;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(5, 3);
        for (int i = 0; i < 100; i++) {
            if (!tokenBucket.allowReq()) {
                Thread.sleep(1000);
            } else {
                System.out.println("req：" + i);
            }
        }
    }

}
