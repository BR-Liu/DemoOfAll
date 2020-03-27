package TestThread.TestCountDown;

import java.io.Closeable;
import java.util.concurrent.CountDownLatch;

/**
 * brliu
 * 为了实现closeable
 */
public class HealthCheckerCountDownLatch extends CountDownLatch implements Closeable {
    /**
     * Constructs a {@code CountDownLatch} initialized with the given count.
     *
     * @param count the number of times {@link #countDown} must be invoked
     *              before threads can pass through {@link #await}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public HealthCheckerCountDownLatch(int count) {
        super(count);
    }

    @Override
    public void close() {
        this.countDown();
    }
}
