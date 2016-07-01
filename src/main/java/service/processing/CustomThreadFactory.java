package service.processing;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private static final String PREFIX = "Track_Processing_Thread-";
    private AtomicInteger threadCount = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(PREFIX + threadCount.incrementAndGet());
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
