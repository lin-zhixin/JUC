public class DeadLockdemo2 {
    public static void main(String[] args) {
        Object o1=new Object();
        Object o2=new Object();

        new Thread(()->{
            synchronized (o1){
                System.out.println("get 1");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2){
                    System.out.println("get 2");
                }

            }
        },"t1").start();
        new Thread(()->{
            synchronized (o2){
                System.out.println("get 2");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1){
                    System.out.println("get 1");
                }

            }
        },"t2").start();
    }
//

}
