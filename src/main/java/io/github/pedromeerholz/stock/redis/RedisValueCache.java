package io.github.pedromeerholz.stock.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisValueCache {
    public final String ALL_ITEMS_KEY = "allItems";
    public final String ENABLED_ITEMS_KEY = "enabledItems";
    public final String DISABLED_ITEMS_KEY = "disabledItems";
    public final String ALL_HISTORY_KEY = "historyItems";
    private final ValueOperations<String, Object> valueOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisValueCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void saveInCache(String key, Object data) {
        this.valueOperations.set(key, data);
    }

    public Object getCachedValue(String key) {
        return this.valueOperations.get(key);
    }

    public void deleteCachedValue(String key) {
        this.valueOperations.getOperations().delete(key);
    }

    public void clearAllCachedValues() {
        this.deleteCachedValue(ALL_ITEMS_KEY);
        this.deleteCachedValue(ENABLED_ITEMS_KEY);
        this.deleteCachedValue(DISABLED_ITEMS_KEY);
        this.deleteCachedValue(ALL_HISTORY_KEY);
    }
}
