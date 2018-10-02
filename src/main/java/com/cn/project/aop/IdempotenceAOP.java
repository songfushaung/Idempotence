package com.cn.project.aop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cn.project.ext.Idempotence;
import com.cn.project.ext.IdempotenceType;
import com.cn.project.redis.RedisPoolAlone;

@Aspect
@Component
public class IdempotenceAOP {
	@Autowired
	private RedisPoolAlone redis;
        /**
         * 
    	 * 
    	 * @methodDesc: 功能描述:拦截controller下的所有请求
    	 * @author: 宋付双
    	 * @param: 
    	 * @createTime:2018年10月2日
    	 * @returnType:@param 
    	 * @copyright:
     */
	@Pointcut("execution(public * com.cn.project.controller.*.*(..))")
	public void rlAop() {
		
	}
	//aop环绕通知
	@Around("rlAop()")
	public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String token=null;
		HttpServletRequest request=getRequest();
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		//判断方法上是否有@Idempotence
		Idempotence idempotence=signature.getMethod().getDeclaredAnnotation(Idempotence.class);
		if(idempotence!=null){
			//获取type中的参数
			String type=idempotence.type();
			if(type==null||type.equals(IdempotenceType.EXITHEAD)){
				//从请求头中获得
				token=request.getHeader("TOKEN");
			}
			else{
				//从表单获取
				token=request.getParameter("TOKEN");
			}
			//token是空
			if(StringUtils.isEmpty(token)){
				response("token is null");
				return null;
			}
			//token不为空,看在redis中是否存在 请勿重复提交
			if(!redis.exists(token)){
				response("Do not repeat submission");
				return null;
			}
			//删除token
			redis.del(token);
		}
		//放行
		Object proceed = proceedingJoinPoint.proceed();
		return proceed;
	}
	public HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		return request;
	}

	public void response(String msg) throws IOException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = attributes.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			writer.println(msg);
		} catch (Exception e) {

		} finally {
			writer.close();
		}

	}
}
