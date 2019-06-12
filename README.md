# spring.boot Mybatis-Shiro-redis-ehcahce

## 已完成功能以及技术栈

1 * 优化每次请求，服务端都要更新Session到缓存的问题，由于项目的Session用的是共享对象，保存在redis中，有必要减少更新redis的操作。
2 * 可以切换ehcache或redis作为shiro缓存
3 * 优化每次请求,都要从redis拉取，因为shiro框架的问题，一次请求会多次调用获取redis中的session，故改为每次请求只拉取一次redis中的session，其他从* request里面获取
4 * session对象本例用Protostuff序列化处理
5 * 通用mapper的使用，附上codesmith代码模板
6 * redis使用Lettuce 相比较 Jedis ，优点更多。


##  TODO LIST
* [jcasbin集成](https://github.com/casbin/jcasbin)

