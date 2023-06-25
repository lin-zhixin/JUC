package productAndComsumer;

import java.util.LinkedList;

public class MessageQueue {

    private LinkedList<Message> list = new LinkedList<>();

    private int capcity;


    public void put(Message message) {
        synchronized (list) {
//            队列满 解锁给消费者消费 等到不满了再生产
            while (list.size() >= capcity) {
                try {
                    System.out.println(Thread.currentThread().getName() + "满等");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "生产了:"+message.getName());
            list.addFirst(message);
            list.notifyAll();
        }

    }

    public Message get() {
        synchronized (list) {
//           队空 解锁给生产者消费 等到不空了再消费
            while (list.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + "空等");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Message message = list.removeLast();
            System.out.println(Thread.currentThread().getName() + "消费了"+message.getName());

            list.notifyAll();
            return message;
        }

    }

    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue();
        mq.capcity = 3;
        for (int i = 0; i < 5; i++) {
            new Thread(mq::get, "c" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                mq.put(new Message(finalI, String.valueOf(finalI)));
            }, "p" + i).start();
        }
    }
}

