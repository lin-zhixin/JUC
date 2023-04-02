package thread;

import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class Create3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
//                while (true) {
                    log.debug("running...");
//                    Thread.sleep(1000);
//                }
//                Thread.sleep(1000);
                return 123456;
            }
        });
        Thread t1 = new Thread(task, "t1");
        t1.start();
        log.debug("{}", task.get());

    }
}
