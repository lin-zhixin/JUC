package exception;

public class Test {

    public void test(int num) throws Exception{
        if (num<0){
            throw new Myexception("num < 0!");
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.test(-100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
