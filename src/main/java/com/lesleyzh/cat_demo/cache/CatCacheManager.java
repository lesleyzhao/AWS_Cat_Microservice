package com.lesleyzh.cat_demo.cache;

import com.lesleyzh.cat_demo.dto.CatDto;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CompletableFuture;

public class CatCacheManager implements DemoCacheManager<String, CatDto> {

    private final RedissonClient redissonClient;

    public CatCacheManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public CatDto get(String key){
        return (CatDto) redissonClient.getBucket(key).get();
    }

    @Override
    public void put(String key, CatDto value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public void evict(String key) {

    }

    @Override
    public void clearCache() {

    }

    @Override
    public CompletableFuture<CatDto> getAsync(String key) {
        RFuture<Object> rFuture = redissonClient.getBucket(key).getAsync();
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (CatDto) rFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

}
