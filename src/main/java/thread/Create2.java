package thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Create2 {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        Thread t = new Thread(r,"runnable");
        t.start();
//2
        Runnable r2 = () -> log.debug("running2");
        Thread t2 = new Thread(r2,"runnable2");
        t2.start();

        log.debug("main running");

    }
}
