package service.processing;

import java.util.concurrent.*;

public class ThreadExecutor extends ThreadPoolExecutor {
    private static final int DEFAULT_CAPACITY = 256;
    private static final int INCREASE_NUMBER_OF_THREADS_FACTOR = 7;
    private static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);
    private static final int numberOfCpuCores = Runtime.getRuntime().availableProcessors();

    private ThreadExecutor(long keepAliveTime,
                           TimeUnit unit,
                           BlockingQueue<Runnable> workQueue) {
        super(numberOfCpuCores,
                numberOfCpuCores * INCREASE_NUMBER_OF_THREADS_FACTOR,
                keepAliveTime,
                unit,
                workQueue,
                new CustomThreadFactory());
    }

    public static ThreadExecutor getTrackProcessingExecutor() {
        return new ThreadExecutor(4, TimeUnit.MILLISECONDS, blockingQueue);
    }
}
