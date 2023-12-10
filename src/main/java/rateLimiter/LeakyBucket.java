package rateLimiter;

//漏桶算法
public class LeakyBucket {
    //    桶容量
    private int cap;
    //    令牌数量
    private int size;
    //    一秒内令牌生成速率
    private int rate;
    //    最后一程生成令牌时间
    private long lastTime;

    public LeakyBucket(int cap, int rate) {
        this.cap = cap;
        this.rate = rate;
        this.size = 0;
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized boolean allowReq() {
        resize();
        if (size < cap) {
            size++;
            return true;
        } else {
            return false;
        }
    }

    private void resize() {
        long now = System.currentTimeMillis();
        long leakNums = (now - lastTime) / 1000 * rate;
        if (leakNums > 0) {
            size = (int) Math.max(0, size - leakNums);
            lastTime = now;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucket leakyBucket = new LeakyBucket(5, 3);
        for (int i = 0; i < 100; i++) {
            if (!leakyBucket.allowReq()) {
                leakyBucket.resize();
                Thread.sleep(1000);
            } else {
                System.out.println("req：" + i);
            }
        }
    }

}
