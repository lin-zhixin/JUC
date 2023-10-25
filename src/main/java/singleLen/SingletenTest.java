package singleLen;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SingletenTest {
    private static volatile SingletenTest singleton;

    public static SingletenTest getInstance() {
//        FixedThreadPool

//        ScheduledThreadPoolExecutor
        if (singleton == null) {
            synchronized (SingletenTest.class) {
                if (singleton == null) {
                    singleton = new SingletenTest();
                }
            }
        }
        return singleton;
    }
}
