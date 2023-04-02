package thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Create {
    public static void main(String[] args) {
        Thread t1=new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t1.setName("t1");
        t1.start();
        log.debug("main running");

    }
}
