## 面试部分

- 线程池的原理

1.keepAliveTime参数的意思是非核心线程的过期时间，就是时间到了非核心线程就销毁
2.只有在新的任务过来的时候核心线程才会创建 否则不会创建
![img.png](img.png)