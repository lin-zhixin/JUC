package thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StackTest {
    public static void main(String[] args) {
        Thread t=new Thread() {
            @Override
            public void run() {
                log.debug("running.....");
            }
        };
        t.setName("thread t");
        t.start();
//        t.start();

        log.debug("main running");
    }
}
