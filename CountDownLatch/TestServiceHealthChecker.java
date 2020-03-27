package TestThread.TestCountDown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * brliu
 * 用来测试应用健康检查器
 */
public class TestServiceHealthChecker {

    private static List<ServiceBaseHealthChecker> services = new ArrayList<>();

    private static boolean allServiceIsHealth = true;

    public static void main(String[] args) throws Exception {
        HealthCheckerCountDownLatch healthCheckerCountDownLatch = new HealthCheckerCountDownLatch(2);

        services.add(new RedisServiceHealthChecker(healthCheckerCountDownLatch));
        services.add(new DBServiceHealthChecker(healthCheckerCountDownLatch));

        Executor executor = new ThreadPoolExecutor(3,
                3, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
        for (ServiceBaseHealthChecker serviceBaseHealthChecker : services) {
            executor.execute(serviceBaseHealthChecker);
        }

        healthCheckerCountDownLatch.await();

        for (ServiceBaseHealthChecker serviceBaseHealthChecker : services) {
            if (!serviceBaseHealthChecker.isHealth()) allServiceIsHealth = false;
        }

        System.out.println("全部服务启动情况: " + allServiceIsHealth);

    }
}
