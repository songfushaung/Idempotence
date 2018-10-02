# Idempotence
 * 解决幂等性提交问题:
 * 1.去重表:在插入数据的时候，插入去重表，利用数据库的唯一索引特性，保证唯一的逻辑。
 * 2.悲观锁:select for update，整个执行过程中锁定该订单对应的记录。
 * 3.token 加 redis:
 *  1.生成token令牌,返回同时,放入到redis
 *  2.在调用接口时,将token放入到请求头中
 *  3.接口去redis查找 如果有,删除token向下执行.
 *  4.重复提交时,因为token已经为空,所以不能继续操作
 * 手写注解限制重复提交
   使用aop看代码
