package com.cn.project.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 
 * @classDesc: 功能描述:自动以注解 解决幂等提交
 * @author: 宋付双
 * @createTime: 2018年10月2日
 * @version: v1.0
 * @copyright:
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotence {
	/*获取token的路径*/
	String type();
}
