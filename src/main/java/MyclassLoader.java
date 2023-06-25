import java.util.concurrent.locks.ReentrantLock;

public class MyclassLoader extends ClassLoader{
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
//    ReentrantLock
//    @Override
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        return super.findClass(name);
//    }
}
