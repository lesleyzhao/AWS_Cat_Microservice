package com.lesleyzh.cat_demo.ratelimiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedissonRateLimiter implements RateLimiter{

    private final RedissonClient redissonClient;

    public RedissonRateLimiter(RedissonClient redissonClient){
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean allowRequest(String apiKey) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(apiKey);
        return rateLimiter.tryAcquire();
    }

    @Override
    public void resetLimit(String apiKey) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(apiKey);
        rateLimiter.delete();
    }

    @Override
    public void setLimit(String apiKey, int limit) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(apiKey);
        rateLimiter.trySetRate(RateType.OVERALL, limit, Duration.ofSeconds(1));
    }
}
