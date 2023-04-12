package utils.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fuhouyin
 * @time 2023/4/11 9:30
 */
@Component
public class RedisTemplateProviderImpl implements RedisTemplateProvider {
    public static final RedisSerializer<Object> JDK_SERIALIZER = new JdkSerializationRedisSerializer();
    public static final RedisSerializer<String> STRING_SERIALIZER = new StringRedisSerializer();
    private final Map<String, RedisTemplate<String, Object>> redisTemplateMap = new HashMap();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r;
    private final Lock w;
    private final RedisConnectionFactory redisStoreConnectionFactory;

    public RedisTemplateProviderImpl(RedisConnectionFactory redisStoreConnectionFactory) {
        this.r = this.rwl.readLock();
        this.w = this.rwl.writeLock();
        this.redisStoreConnectionFactory = redisStoreConnectionFactory;
    }

    public RedisTemplate<String, Object> getTemplate() {
        return this.getTemplate(STRING_SERIALIZER);
    }

    public RedisTemplate<String, Object> getTemplate(RedisSerializer valueSerializer) {
        return this.getTemplate(STRING_SERIALIZER, valueSerializer);
    }

    private RedisTemplate<String, Object> getTemplate(RedisSerializer<String> keySerializer, RedisSerializer valueSerializer) {
        String cacheKey = this.getTemplateCacheKey(keySerializer, valueSerializer);

        try {
            this.r.lock();
            RedisTemplate<String, Object> template = (RedisTemplate)this.redisTemplateMap.get(cacheKey);
            if (template != null) {
                RedisTemplate var5 = template;
                return var5;
            }
        } finally {
            this.r.unlock();
        }

        return this.createAndPut(keySerializer, valueSerializer, cacheKey);
    }

    private RedisTemplate<String, Object> createAndPut(RedisSerializer<String> keySerializer, RedisSerializer valueSerializer, String cacheKey) {
        RedisTemplate var5;
        try {
            this.w.lock();
            RedisTemplate<String, Object> template = (RedisTemplate)this.redisTemplateMap.get(cacheKey);
            if (template == null) {
                template = this.createRedisTemplate(keySerializer, valueSerializer);
                this.redisTemplateMap.put(cacheKey, template);
            }

            var5 = template;
        } finally {
            this.w.unlock();
        }

        return var5;
    }

    private RedisTemplate<String, Object> createRedisTemplate(RedisSerializer<String> keySerializer, RedisSerializer valueSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.setValueSerializer(valueSerializer);
        template.setConnectionFactory(this.redisStoreConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    private String getTemplateCacheKey(RedisSerializer keySerializer, RedisSerializer valueSerializer) {
        Assert.notNull(keySerializer, "keySerializer should not null");
        Assert.notNull(valueSerializer, "valueSerializer should not null");
        return keySerializer.getClass().getName() + "-" + valueSerializer.getClass().getName();
    }
}
