import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

public class ReferenceDemo {

    public static void strongreference(String[] args) {
//        强引用
//        StrongReference myBoject = new StrongReference();
//        System.out.println("gc befor:"+myBoject);
//        myBoject=null;
//        System.gc();
//        System.out.println("gc after:"+myBoject);
    }

    public static void main(String[] args) throws InterruptedException {
        //      软引用
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());

//        ThreadLocal
        System.gc();
        TimeUnit.SECONDS.sleep(1);

        System.out.println("内存够用：" + softReference.get());

        try {
            byte[] bytes = new byte[2* 1024 * 1024];

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("内存不够用：" + softReference.get());

        }

    }
}

class MyObject {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("---finalize");
//        super.finalize();
    }
}
