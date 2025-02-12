package com.lesleyzh.cat_demo.ratelimiter;

public interface RateLimiter {
    boolean allowRequest(String apiKey);
    void resetLimit(String apiKey);
    void setLimit(String apiKey, int limit);
}
