package com.cn.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.project.redis.RedisPoolAlone;
import com.cn.project.service.CreateToken;
import com.cn.project.utils.TokenUtils;
@Service
public class CreateTokenImpl implements CreateToken {
	@Autowired
	private RedisPoolAlone redis;
	/**
	 * 生成token
	 */
	@Override
	public String newToken() {
		String token=TokenUtils.getToken();
		//放入redis
		redis.set(token, token);
		//存放时间
		redis.expire(token, 1800);
		return token;
	}

}
