package thread;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor t = new ThreadPoolExecutor(2, 3, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            t.execute(() -> {
                System.out.println("execute"+finalI);
            });
        }
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            System.out.println(t.submit(() -> {
                System.out.println("submit" + finalI);
            },"submit end!!!").get());
        }

    }
}


