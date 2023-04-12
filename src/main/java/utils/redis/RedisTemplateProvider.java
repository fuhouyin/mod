package utils.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author fuhouyin
 * @time 2023/4/11 9:30
 */
public interface RedisTemplateProvider {
    RedisTemplate<String, Object> getTemplate();

    RedisTemplate<String, Object> getTemplate(RedisSerializer valueSerializer);
}
