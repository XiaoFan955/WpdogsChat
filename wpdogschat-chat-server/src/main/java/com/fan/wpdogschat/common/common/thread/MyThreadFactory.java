package com.fan.wpdogschat.common.common.thread;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * 装饰器模式
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {
    private static final MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER  = new MyUncaughtExceptionHandler();
    private ThreadFactory original;
    @Override
    public Thread newThread(Runnable r) {
        //执行spring线程自己的创建逻辑
        Thread thread = original.newThread(r);
        //额外装饰的逻辑
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);
        return thread;
    }
}
