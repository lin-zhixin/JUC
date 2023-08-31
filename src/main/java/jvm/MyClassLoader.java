package jvm;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureClassLoader;

public class MyClassLoader extends SecureClassLoader {

    //    要加载的类的地址
    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = this.classPath + name.replace(".", "\\").concat(".myclass");
//        System.out.println(filePath);
        FileInputStream fis;
        ByteArrayBuffer buf = new ByteArrayBuffer();
        byte[] b;
        int code;
        try {
            fis = new FileInputStream(new File(filePath));
            while ((code = fis.read()) != -1) {
                buf.write(code);
            }
            b = buf.toByteArray();
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        return super.loadClass(name, resolve);
        return null;
    }
}
