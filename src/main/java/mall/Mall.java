package mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Mall {
    //    电商网站比价需求分析
//1需求说明
//1.1同一款产品，同时搜索出同款产品在各大电商平台的售价；
//            1.2同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少
//2输出返回：
//    出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
//《mysql》 in jd price is 88.05
//            «mysql》 in dangdang price is 86.11
//            《mysql》 in taobao price is 90.43
//            3解决方案，比对同一个商品在各个平台上的价格，要求获得一个清单列表，
//            1 step by step，按部就班，查完京东查淘宝，查完淘宝查天猫.....
//            ，万箭齐发，一口气多线程异步任务同时查询。。。。。
//            2 all in

    static List<NetMall> list = Arrays.asList(new NetMall("jd"),
            new NetMall("dandan"),
            new NetMall("tb"),
            new NetMall("tb1"),
            new NetMall("tb2"),
            new NetMall("tb3"),
            new NetMall("tb4"));

    public static List<String> getPrice(List<NetMall> list, String name) {
        return list.stream()
                .map(netMall -> String.format(name + "%s:%.2f", netMall.getName(), netMall.calcPrice()))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String name) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<String> ress = new ArrayList<>();
        for (NetMall netMal : list) {
            new Thread(() -> {
                System.out.printf(name + "%s:%.2f%n", netMal.getName(), netMal.calcPrice());
            }).start();
//            CompletableFuture.supplyAsync(() -> {
//                System.out.printf(name + "%s:%.2f%n", netMal.getName(), netMal.calcPrice());
//                return 1;
//            }, pool);
        }
        pool.shutdown();
//        Thread
        return ress;
//        return list.stream()
//                .map(netMall ->
//                        CompletableFuture.supplyAsync(() -> String.format(name + "%s:%.2f", netMall.getName(), netMall.calcPrice())))
//                .collect(Collectors.toList())
//                .stream()
//                .map(CompletableFuture::join)
//                .collect(Collectors.toList());
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
//        getPrice(list, "mysql");

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        startTime = System.currentTimeMillis();
        CompletableFuture<List<String>> c = CompletableFuture.supplyAsync(() -> {
            try {
                return getPriceByCompletableFuture(list, "mysql");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });

//        List<String> res = getPriceByCompletableFuture(list, "mysql");
        System.out.println(c.join());

        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
//        getPrice(list, "mysql");

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        startTime = System.currentTimeMillis();

        List<String> res = getPriceByCompletableFuture(list, "mysql");
//        System.out.println(res);

        endTime = System.currentTimeMillis();
        System.out.println("time:" + (endTime - startTime));
    }


}

@Data
@AllArgsConstructor
class NetMall {

    private String name;

    public double calcPrice() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2;
    }

}
