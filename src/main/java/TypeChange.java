import org.apache.logging.log4j.spi.CopyOnWrite;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;

public class TypeChange {
    public static void main(String[] args) {

        int i=-1024;
        byte b= (byte) i;
//        CopyOnWriteArrayList
//        Collections.synchronizedList()
        double da=0.1,db=0.2;
        System.out.println(da+db);
//        System.out.println(b);
    }
}
