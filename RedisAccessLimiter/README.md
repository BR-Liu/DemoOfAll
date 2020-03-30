基于redis + lua的简易可插拔的限流spring boot starter工具
@author brliu

## 使用说明

1. 开启限流能力：在SpringBoot启动类上添加 @EnableAccessLimiter
2. 为方法配置限流：在方法处添加 @AccessLimiter 注解。
```
@AccessLimiter 注解有两个属性：
1.limit 方法在1s内允许的访问量，默认为10.
2.methodName 方法名，用于在redis生成该方法限流时对应的键，默认为入口方法名 + 该方法的入参列表.
```
3. 组件默认实现方式是基于每秒限流一定数量，但是同时也支持自定义lua脚本进行自定义限流逻辑。需要在classpath任意目录下添加lua脚本，并在配置文件中配置以下两个属性：
```
1.access.limit.classPath // 脚本在classpath下的相对路径
2.access.limit.fileName // 脚本文件名，脚本接收的参数应为 @AccessLimiter 注解中的属性（未来的版本中会扩展该注解属性）
```
