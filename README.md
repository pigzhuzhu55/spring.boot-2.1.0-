# spring.boot Mybatis-Shiro-redis-ehcache

## 技术栈
- spring.boot
- mybatis+通用mapper
- shiro
- redis(lettuce)
- ehcache
## 初步功能
- 项目模块分离，dao、pojo、common、web、service。
- 框架基于前后端分离，登录验证、权限部分暂时使用shiro，唉，重写好多类。
- shiro：
* 优化每次请求，AbstractSessionDAO都要更新Session到缓存的问题，由于项目的Session用的是共享对象，保存在外部缓存中，有必要减少更新缓存的操作。
* 支持切换ehcache或redis作为shiro缓存。
* 优化每次请求，因为shiro框架的问题，doReadSession多次调用，都是直接从缓存中拉取，改成增强request的上下文，避免多次请求，影响性能。 
* session对象本例用Protostuff序列化处理
- 通用mapper的使用，附上codesmith代码模板
- redis使用Lettuce 相比较 Jedis ，优点更多。
- 单用户多处登录互踢功能


##  TODO LIST
* [jcasbin集成](https://github.com/casbin/jcasbin)
* [j2cache]
* 前端部分

