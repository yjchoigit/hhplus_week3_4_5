package com.hhplus.hhplus_week3_4_5.ecommerce.facade.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.order.OrderService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class ProductOrderFacade {
    private final ProductService productService;
    private final OrderService orderService;

    public List<FindProductRankingApiResDto> findProductRanking(ProductEnums.Ranking rankingType){
        // 랭킹타입 별 상위 상품 조회
        List<Object[]> topProductList = orderService.findTopProductsByBuyCnt(rankingType);

        if(topProductList.isEmpty()) {
            return new ArrayList<>();
        }

        List<FindProductRankingApiResDto> rankingList = new ArrayList<>();

        for(Object[] row : topProductList){
            // 상품 id
            Long productId = (Long) row[0];
            // 총 주문 구매수량
            Long totalBuyCnt = (Long) row[1];

            // 상품 정보 조회
            Product product = productService.findProductByProductId(productId);
            // 리스트 추가
            rankingList.add(FindProductRankingApiResDto.from(product, totalBuyCnt.intValue()));
        }

        return rankingList;
    }
}
