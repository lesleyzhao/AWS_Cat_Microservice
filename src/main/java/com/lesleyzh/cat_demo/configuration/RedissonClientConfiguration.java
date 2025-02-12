package com.lesleyzh.cat_demo.configuration;


import java.util.HashMap;
import java.util.Map;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonClientConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        //如果需要配置多台就这么往里面加
        //        config.useClusterServers()
        //                .addNodeAddress("redis://
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
       Map<String, CacheConfig> config = new HashMap<>();
       config.put("cat_info", new CacheConfig(24*60*1000, 12*60*1000));

       return (CacheManager) new RedissonSpringCacheManager(redissonClient, config);
    }
}
