package productAndComsumer;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    List<Message> list = new ArrayList<>();
    int size = 0;

    public void put(Message message) throws InterruptedException {
        synchronized (list) {
            while (list.size() >= size) {
                list.wait();
            }
            System.out.println("add" + message.getName());
            list.add(message);
            list.notifyAll();
        }
    }

    public void get() throws InterruptedException {

        synchronized (list) {
            while (list.size() <= 0) {
                list.wait();
            }
            System.out.println("get" + list.get(0).getName());
            list.remove(0);
            list.notifyAll();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Queue q = new Queue();
        q.size = 3;
        for (Integer i = 0; i < 5; i++) {
            Integer finalI = i;
            new Thread(() -> {
                try {
                    q.put(new Message(finalI, finalI.toString()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (Integer i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    q.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
