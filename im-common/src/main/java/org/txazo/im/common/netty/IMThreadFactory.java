package org.txazo.im.common.netty;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.ObjectUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IMThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolId = new AtomicInteger();

    private final AtomicInteger nextId = new AtomicInteger();
    private final String prefix;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup threadGroup;

    public IMThreadFactory(String poolName) {
        this(poolName, false, Thread.NORM_PRIORITY, Thread.currentThread().getThreadGroup());
    }

    public IMThreadFactory(String poolName, boolean daemon, int priority, ThreadGroup threadGroup) {
        if (poolName == null) {
            throw new NullPointerException("poolName");
        }
        if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException("priority: " + priority + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
        }

        prefix = poolName + '-' + poolId.incrementAndGet() + '-';
        this.daemon = daemon;
        this.priority = priority;
        this.threadGroup = ObjectUtil.checkNotNull(threadGroup, "threadGroup");
    }

    public Thread newThread(Runnable r) {
        Thread t = newThread(new IMThreadFactory.DefaultRunnableDecorator(r), prefix + nextId.incrementAndGet());
        try {
            if (t.isDaemon()) {
                if (!daemon) {
                    t.setDaemon(false);
                }
            } else {
                if (daemon) {
                    t.setDaemon(true);
                }
            }

            if (t.getPriority() != priority) {
                t.setPriority(priority);
            }
        } catch (Exception ignored) {
        }
        return t;
    }

    protected Thread newThread(Runnable r, String name) {
        return new FastThreadLocalThread(threadGroup, r, name);
    }

    private static final class DefaultRunnableDecorator implements Runnable {

        private final Runnable r;

        DefaultRunnableDecorator(Runnable r) {
            this.r = r;
        }

        @Override
        public void run() {
            try {
                r.run();
            } finally {
                FastThreadLocal.removeAll();
            }
        }

    }

}
