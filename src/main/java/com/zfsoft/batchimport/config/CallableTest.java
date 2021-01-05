package com.zfsoft.batchimport.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author chenyq
 * @Description:
 * @date 2020/7/3 15:23
 */
public class CallableTest {

    public static void main(String[] args) throws Exception {
        System.out.println("使用线程池 invokeAll 运行一组 Callable 任务：");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建大小固定为 5 的线程池

        List<AccumCallable> tasks = new ArrayList<>(10); // tasks 为一组任务
        for (int i = 0; i < 10; i++) {
            tasks.add(new AccumCallable(i * 10 + 1, (i + 1) * 10));
        }

        List<Future<Integer>> futures = threadPool.invokeAll(tasks); // 阻塞，直到所有任务都运行完毕

        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // 返回任务的结果
        }

        System.out.println("Total: " + total);

        threadPool.shutdown(); // 向线程池发送关闭的指令
    }

    /*
    public static void main(String[] args) throws Exception {
        System.out.println("使用线程池运行 Callable 任务：");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建大小固定为 5 的线程池

        List<Future<Integer>> futures = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            AccumCallable task = new AccumCallable(i * 10 + 1, (i + 1) * 10);
            Future<Integer> future = threadPool.submit(task); // 提交任务
            futures.add(future);
        }
        threadPool.shutdown(); // 向线程池发送关闭的指令，等到已经提交的任务都执行完毕之后，线程池会关闭

        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // 阻塞，直到任务结束，返回任务的结果
        }

        System.out.println("Total: " + total);
    }*/

    static final class AccumCallable implements Callable<Integer> {

        private final int begin;
        private final int end;

        public AccumCallable(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            int result = 0;
            for (int i = begin; i <= end; i++) {
                result += i;
                Thread.sleep(100);
            }
            System.out.printf("(%s) - 运行结束，结果为 %d\n",
                    Thread.currentThread().getName(), result);

            return result;
        }

    }

}
