package com.cn.project.redis;

public interface RedisUtils {
	  /**
     * 保存
     * 
     * @param key
     *            键
     * @param value
     *            zhi
     */
    public String set(String key, String value);



    /**
     * 根据key查询
     * 
     * @param key
     *            键
     * @return 值
     */
    public String get(String key);
    /**
     * key是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key);

    /**
     * 删除
     * 
     * @param key
     *            键
     */
    public Long del(String key);

    /**
     * 根据key设置生存时间
     * 
     * @param key
     *            键
     * @param seconds
     *            时间，秒s为单位
     */
    public Long expire(String key, int seconds);
    /**
     * 返回给定 key 的剩余生存时间
     * @param key
     * @return
     */
    public Long ttl(String key);

    /**
     * value加一<br/>
     * 注意key必须是整型
     * 
     * @param key
     *            键
     * @return 加一后的结果
     */
    public Long incr(String key);
	
	public Long hset(String key, String field, String value);
	
	public String hget(String key, String field);
	
	public Long hdel(String key, String... field);
}
