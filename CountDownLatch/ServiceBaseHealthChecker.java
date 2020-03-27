package TestThread.TestCountDown;

import lombok.Data;

/**
 * brliu
 * 使用闭锁（CountDown）实现一个建议的全局服务启动健康检查
 */
@Data
public abstract class ServiceBaseHealthChecker implements Runnable {

    private String serviceName;
    private boolean isHealth;
    private final HealthCheckerCountDownLatch allServiceCountDownLatch;

    public ServiceBaseHealthChecker(String serviceName, HealthCheckerCountDownLatch countDownLatch) {
        this.serviceName = serviceName;
        this.allServiceCountDownLatch = countDownLatch;
        this.isHealth = false;
    }

    public abstract boolean verifyHealthyStatus();

    @Override
    public void run() {
        try (allServiceCountDownLatch) {
            this.isHealth = verifyHealthyStatus();
        }
    }
}
