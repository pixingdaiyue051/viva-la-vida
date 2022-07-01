package com.tequeno.conutil;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列推荐使用 LinkedBlockingQueue 虽然设置了容量 但不会预先开辟对应的内存
 * 只有使用到了put类方法才实际开辟内存写入数据 当需要使用到的容量较大是时内存使用效率上会高于 ArrayBlockingQueue
 * 如果队列的容量可以预先明确设定 使用中不存在超出队列问题时推荐使用offer入队 poll出队
 */
public class QueueTest {

    public static void main(String[] args) {
        QueueTest test = new QueueTest();
//        test.arrayBlockingQueue();


        test.stack();
    }

    /**
     * 阻塞队列 LinkedBlockingQueue
     */
    public void linkedBlockingQueue() {
        try {
            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(); // 默认使用Integer.MAX_VALUE容量
            queue.add("4erwf"); // LinkedBlockingQueue 没有实现add 使用了AbstractQueue的add 本质上还是offer方法
            queue.put("12344");
            queue.offer("bfdfg"); // 推荐使用

            final String take = queue.take();
            final String poll = queue.poll(); // 推荐使用
            final String peek = queue.peek(); // 获得头指针数据但不出队

            System.out.println(take);
            System.out.println(poll);
            System.out.println(peek);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 阻塞队列 ArrayBlockingQueue
     */
    public void arrayBlockingQueue() {
        try {
            ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
            queue.add("cbcvb"); // ArrayBlockingQueue 没有实现add 使用了AbstractQueue的add 本质上还是offer方法
            queue.put("434dg");
            queue.offer("3467664"); // 推荐使用

            final String take = queue.take();
            final String poll = queue.poll(); // 推荐使用
            final String peek = queue.peek(); // 获得头指针数据但不出队

            System.out.println(take);
            System.out.println(poll);
            System.out.println(peek);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delayedQueue() {
    }

    public void stack() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(2);
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());

    }
}