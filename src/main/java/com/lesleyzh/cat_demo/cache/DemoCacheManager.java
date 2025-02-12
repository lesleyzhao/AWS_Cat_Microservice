package com.lesleyzh.cat_demo.cache;

import java.util.concurrent.CompletableFuture;

public interface DemoCacheManager<K, V>{
    V get(K key);
    void put(K key, V value);
    void evict(K key);
    void clearCache();
    CompletableFuture<V> getAsync(K key);
}
