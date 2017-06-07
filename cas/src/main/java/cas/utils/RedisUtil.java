package cas.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {
    
    private static String ADDR = "127.0.0.1";
    
    private static int PORT = 6379;
    
    private static int MAX_ACTIVE = 1024;
    
    private static int MAX_IDLE = 200;
    
    private static int TIMEOUT = 10000;
    
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    private static ThreadLocal<Jedis> threadLocal = new ThreadLocal<Jedis>();
    
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Jedis getJedis() {
    	Jedis resource = null;
        try {
        	resource = threadLocal.get();
        	if(resource != null){
        		return resource;
        	}
        	resource = jedisPool.getResource();
        	threadLocal.set(resource);
        	return resource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resource;
    }

    public static void returnResource() {
    	Jedis jedis = threadLocal.get();
        if (jedis != null) {
        	threadLocal.remove();
        	jedis.close();
        }
    }
}