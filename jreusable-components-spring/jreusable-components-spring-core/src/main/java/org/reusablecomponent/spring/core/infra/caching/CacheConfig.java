package org.reusablecomponent.spring.core.infra.caching;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    CaffeineCacheManager cacheManager() {

	final var caffeineBuilder = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .initialCapacity(100)
                .maximumSize(1000);

	final var caffeineCacheManager = new CaffeineCacheManager();
	caffeineCacheManager.setCaffeine(caffeineBuilder);
	return caffeineCacheManager;
    }
}
