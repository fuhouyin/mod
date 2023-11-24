package utils.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author fuhouyin
 * @time 2023/4/11 9:30
 */
@Slf4j
@Component
public class RedisCacheUtils {

    @Autowired
    private RedisTemplateProvider redisTemplateProvider;
    @Autowired
    private RedisTemplate redisTemplate;
    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    public static final String SETNX_SCRIPT = "return redis.call('setnx',KEYS[1], ARGV[1])";

    public void setValue(String key, Object value){
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.opsForValue().set(key,value);
    }

    public void setValue(String key, Object value,Long timeout, TimeUnit timeUnit){
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.opsForValue().set(key,value,timeout, timeUnit);
    }

    public Object getValue(String key){
        RedisTemplate redisTemplate = getRedisTemplate();
        return redisTemplate.opsForValue().get(key);
    }

    public String getCacheKey(Object... keys) {
        return StringUtils.join(Arrays.asList(keys), ":");
    }

    public Long hashIncrement(String key, String hashKey){
        RedisTemplate redisTemplate = getRedisTemplate();
        Long increment = redisTemplate.opsForHash().increment(key, hashKey, 1);
        return increment;
    }

    public boolean hasKey(String key){
        return getRedisTemplate().hasKey(key);
    }

    public boolean delKey(String key){
        if (StringUtils.isEmpty(key)) {
            return Boolean.FALSE;
        }
        RedisTemplate redisTemplate = getRedisTemplate();
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.delete(key);
        }
        return Boolean.TRUE;
    }

    public boolean hashHasKey(String key, String hashKey){
        return getRedisTemplate().opsForHash().hasKey(key, hashKey);
    }

    public Object hashGet(String key, String hashKey){
        return getRedisTemplate().opsForHash().get(key, hashKey);
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate<String, Object> template = redisTemplateProvider.getTemplate(stringRedisSerializer);
        return template;
    }

    public void hashPut(String key,String hashKey, String value){
        getRedisTemplate().opsForHash().put(key,hashKey,value);
    }

    public void hashPutIfAbsent(String key, String hashKey, String value) {
        getRedisTemplate().opsForHash().putIfAbsent(key,hashKey,value);
    }

    public Boolean expire(String key, long time, TimeUnit timeUnit){
        Boolean result = getRedisTemplate().expire(key, time, timeUnit);
        return result;
    }

    /**
     * 指定缓存失效时间
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }
    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(毫秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    public Boolean lock(String key){
        return getRedisTemplate().opsForValue().setIfAbsent(key, BigDecimal.ZERO.intValue());
    }

    /**
     * redis实现分布式锁
     * @param key time是：毫秒毫秒毫秒毫秒毫秒
     * @return
     */
    public boolean lock(String key,Long time) {
        //自定义脚本
        DefaultRedisScript<List> script = new DefaultRedisScript<>(SETNX_SCRIPT, List.class);
        //执行脚本,传入参数,由于value没啥用,这里随便写死的"1"
        List<Long> rst = (List<Long>) redisTemplate.execute(script, Collections.singletonList(key), "1");
        //返回1,表示设置成功,拿到锁
        if(rst.get(0) == 1){
            log.info(key+"成功拿到锁");
            //设置过期时间
            expire(key,time);
            log.info(key+"已成功设置过期时间:"+time +" 秒");
            return true;
        }else{
            long expire = getExpire(key);
            log.info(key+"未拿到到锁,还有"+expire+"释放");
            return false;
        }
    }

    public Boolean tryLock(String key){
        try {
            for (int i = 0; i < 3; i++) {
                if (lock(key)) {
                    return Boolean.TRUE;
                }
                Thread.sleep(100L);
            }
        } catch (InterruptedException e) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public void unlock(String key){
        getRedisTemplate().delete(key);
    }


    public Long increment(String key, Long delta){
        return getRedisTemplate().opsForValue().increment(key,delta);
    }

    public Boolean setIfAbsent(String key, Object value){
        return getRedisTemplate().opsForValue().setIfAbsent(key,value);
    }

    public Set<String> keys(String keyPattern) {
        return getRedisTemplate().keys(keyPattern);
    }

    public Object getAndSet(String key, Object value){
        return getRedisTemplate().opsForValue().getAndSet(key,value);
    }
}
