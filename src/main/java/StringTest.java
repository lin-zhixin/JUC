public class StringTest {
    public static void main(String[] args) {
        String str1 = "hello"; //str1指向静态区
        String str2 = new String("hello"); //str2本身是一个堆上的对象指向了方法区里面常量池的"hello"对象
        String str3 = "hello";
        String str4 = new String("hello");
        System.out.println(str1.equals(str2));//true，String的equal比较的是值
        System.out.println(str2.equals(str4)); //true String的equal比较的是值
        System.out.println(str1 == str3); //true 两个地址都是常量池中 "hello" 的地址
        System.out.println(str1 == str2); //false ：==比较的是地址 两个对象地址不同（一个在方法区中，一个在堆中），地址不同
        System.out.println(str2 == str4); //false ：==比较的是地址 根据上面的解释，str2 和str4 是两个不同的对象（在堆中的两个不同对象），所以地址不同
        System.out.println(str2 == "hello"); //false //同：System.out.println(str1 == str2); 这边的"hello"就是方法区中的那个"hello"，是同一个
        str2 = str1;
        System.out.println(str2 == "hello");//true  让str2 指向方法区中的那个"hello"，结果相同

        String str5 = "h" + "ello";//创建了0个对象直接使用上面创建了运行时常量池中的 "hello"
        System.out.println(str1 == str5);//
        String str6 = getH() + "ello";//创建了1个对象 getH()的值不确定 没法直接折叠 所以会使用Stringbuffer先创建一个空对象 之后通过append拼接之后使用tostring返回一个新对象
        System.out.println(str1 == str6);//false
        String str7=getHello();
        System.out.println(str1 ==str7);//
    }

    public static String getH() {
        return "h";
    }
    public static String getHello() {
        return "hello";
    }
}
