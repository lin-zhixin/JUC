package singleLen;

import java.util.Objects;

public class SingletenTest {
    private volatile static SingletenTest instance;

    public static SingletenTest getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (SingletenTest.class) {
                if (Objects.isNull(instance)) {
                    instance = new SingletenTest();
                }
            }
        }
        return instance;
    }
}
