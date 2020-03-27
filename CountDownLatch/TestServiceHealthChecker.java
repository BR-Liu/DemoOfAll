package TestThread.TestCountDown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * brliu
 * 用来测试应用健康检查器
 */
public class TestServiceHealthChecker {

    private static List<ServiceBaseHealthChecker> services = new ArrayList<>();

    private static boolean allServiceIsHealth = true;

    private final static int LATCH_COUNT = 2;

    public static void main(String[] args) throws Exception {
        HealthCheckerCountDownLatch healthCheckerCountDownLatch = new HealthCheckerCountDownLatch(LATCH_COUNT);

        services.add(new RedisServiceHealthChecker(healthCheckerCountDownLatch));
        services.add(new DBServiceHealthChecker(healthCheckerCountDownLatch));

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
        //允许核心线程数过期
        executor.allowCoreThreadTimeOut(true);

        for (ServiceBaseHealthChecker serviceBaseHealthChecker : services) {
            executor.execute(serviceBaseHealthChecker);
        }

        healthCheckerCountDownLatch.await();

        for (ServiceBaseHealthChecker serviceBaseHealthChecker : services) {
            if (!serviceBaseHealthChecker.isHealth()) {
                allServiceIsHealth = false;
                break;
            }
        }

        System.out.println("全部服务启动情况: " + allServiceIsHealth);
        executor.shutdown();
    }
}
