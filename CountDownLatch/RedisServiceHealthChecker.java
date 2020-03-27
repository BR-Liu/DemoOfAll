package TestThread.TestCountDown;

import java.util.concurrent.TimeUnit;


/**
 * brliu
 * 模拟检测redis服务
 */
public class RedisServiceHealthChecker extends ServiceBaseHealthChecker {

    private final static String serviceName = "REDIS_SERVICE";

    public RedisServiceHealthChecker(HealthCheckerCountDownLatch countDownLatch) {
        super(serviceName, countDownLatch);
    }

    //执行模拟检查，实际逻辑可以根据实际需要自行编写
    @Override
    public boolean verifyHealthyStatus() {
        System.out.println("Checking service : " + serviceName);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Checking service " + serviceName + " completed : " + true);
        return true;
    }
}
