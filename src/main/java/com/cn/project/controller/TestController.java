package com.cn.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.project.bean.Order;
import com.cn.project.ext.Idempotence;
import com.cn.project.ext.IdempotenceType;
import com.cn.project.redis.RedisPoolAlone;
import com.cn.project.service.CreateToken;
import com.cn.project.service.OrderService;

/**
 * 解决幂等性提交问题:
 * 1.去重表:在插入数据的时候，插入去重表，利用数据库的唯一索引特性，保证唯一的逻辑。
 * 2.悲观锁:select for update，整个执行过程中锁定该订单对应的记录。
 * 3.token 加 redis:
 *  1.生成token令牌,返回同时,放入到redis
 *  2.在调用接口时,将token放入到请求头中
 *  3.接口去redis查找 如果有,删除token向下执行.
 *  4.重复提交时,因为token已经为空,所以不能继续操作
 * 手写注解限制重复提交
 * 
 * @classDesc: 功能描述:
 * @author: 宋付双
 * @createTime: 2018年10月1日
 * @version: v1.0
 * @copyright:
 */
@RestController
public class TestController {
	@Autowired
	private RedisPoolAlone redis;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CreateToken tokenService;
    @RequestMapping("/getToken")
    public String getToken(){
    	   return tokenService.newToken();
       }
	   /**
	     *   调用接口
		 * 
		 * @methodDesc: 功能描述:
		 * @author: 宋付双
		 * @param: 
		 * @createTime:2018年10月1日
		 * @returnType:@param 
		 * @copyright:
	 */
    @RequestMapping("/addOrder")
	public String AddOrder( Order order,HttpServletRequest request){
		//获取请求头中的token
		String token=request.getHeader("TOKEN");
		if(StringUtils.isEmpty(token)){
			return "token为空";
		}
		if(!redis.exists(token)){
			return "token不存在";
		}
		//删除token
		redis.del(token);
		//执行业务
		int flag=orderService.addOrder(order);
		return flag>0?"添加成功":"添加失败";
		
	}
	   /**
     *   调用接口
	 * 
	 * @methodDesc: 功能描述:使用自己定义的注解
	 * @author: 宋付双
	 * @param: 
	 * @createTime:2018年10月1日
	 * @returnType:@param 
	 * @copyright:
 */
	@RequestMapping("/addOrders")
	@Idempotence(type=IdempotenceType.EXITHEAD)
	public String AddOrders( Order order,HttpServletRequest request){
		//执行业务
		int flag=orderService.addOrder(order);
		return flag>0?"添加成功":"添加失败";
		
}
}
