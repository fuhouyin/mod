package utils.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author fuhouyin
 * @time 2023/4/11 9:30
 */
@Component
public class RedisCacheUtils {

    @Autowired
    private RedisTemplateProvider redisTemplateProvider;
    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

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

    public Boolean lock(String key){
        return getRedisTemplate().opsForValue().setIfAbsent(key, BigDecimal.ZERO.intValue());
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
