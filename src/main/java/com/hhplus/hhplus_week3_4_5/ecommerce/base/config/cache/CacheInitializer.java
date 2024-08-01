package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.cache;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CacheInitializer {
    private final CacheManager cacheManager;

    @PostConstruct
    public void init() {
        // 모든 캐시 이름을 가져옴
        Collection<String> cacheNames = cacheManager.getCacheNames();

        // 각 캐시를 순회하며 초기화
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear(); // 캐시 초기화
            }
        }
    }
}
