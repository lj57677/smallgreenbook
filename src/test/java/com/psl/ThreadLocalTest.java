package com.psl;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocal() {
        ThreadLocal tl = new ThreadLocal();

        new Thread(() -> {
            tl.set("萧炎");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "土豆").start();

        new Thread(() -> {
            tl.set("唐三");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "三少").start();
    }
}
