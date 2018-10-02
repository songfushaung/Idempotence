package com.cn.project.utils;

import java.util.UUID;

public class TokenUtils {
	public static synchronized String getToken() {
		// 1.生成令牌
		String token = "token-" + UUID.randomUUID().toString();

		return token;
	}
}
