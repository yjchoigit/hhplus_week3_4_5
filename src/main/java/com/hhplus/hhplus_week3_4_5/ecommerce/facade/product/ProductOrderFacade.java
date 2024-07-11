package com.hhplus.hhplus_week3_4_5.ecommerce.facade.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.GetProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.order.OrderService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class ProductOrderFacade {
    private final ProductService productService;
    private final OrderService orderService;

    public List<GetProductRankingApiResDto> findProductRanking(){
        // 최근 3일간 가장 많이 팔린 상위 5개 상품 정보
        LocalDateTime startDatetime = LocalDateTime.now().minusDays(3);
        LocalDateTime endDatetime = LocalDateTime.now();
        orderService.findTopProductsBySales(startDatetime, endDatetime);
        return null;
    }
}
