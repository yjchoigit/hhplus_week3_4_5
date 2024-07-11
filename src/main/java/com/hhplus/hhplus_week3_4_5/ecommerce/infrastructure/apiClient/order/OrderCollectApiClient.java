package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order.dto.SendOrderToCollectionDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderCollectApiClient {
    private final RestTemplate restTemplate;

    public OrderCollectApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public void sendOrderToCollectionPlatform(SendOrderToCollectionDto dto) {
        String url = "http://mockapi/order-collection";
        restTemplate.postForObject(url, dto, SendOrderToCollectionDto.class);
    }
}
