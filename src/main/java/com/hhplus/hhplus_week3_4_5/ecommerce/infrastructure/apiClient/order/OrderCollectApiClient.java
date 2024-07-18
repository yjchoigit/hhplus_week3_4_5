package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order.dto.SendOrderToCollectionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OrderCollectApiClient {
    private final RestTemplate restTemplate;

    public OrderCollectApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public void sendOrderToCollectionPlatform(SendOrderToCollectionDto dto) {
        log.info("주문 데이터 수집 >> 외부 데이터 플랫폼 전달");
        String url = "https://httpbin.org/post";
        restTemplate.postForObject(url, dto, SendOrderToCollectionDto.class);
    }
}
