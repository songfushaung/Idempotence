package com.cn.project.ext;

public interface IdempotenceType {
    /*从头部得到token*/
	public static final String EXITHEAD="head";
	/*从表单得到token*/
	public static final String EXITFORM="form";
}
