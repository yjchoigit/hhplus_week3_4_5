package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemSheetRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderSheetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderSheetServiceImpl implements OrderSheetService {
    private OrderSheetRepository orderSheetRepository;
    private OrderItemSheetRepository orderItemSheetRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CreateOrderSheetApiResDto createOrderSheet(CreateOrderSheetApiReqDto reqDto) {
        // 주문서 등록
        OrderSheet orderSheet = orderSheetRepository.save(OrderSheet.builder()
                        .buyerId(reqDto.buyerId())
                        .buyerName(reqDto.buyerName())
                        .allBuyCnt(reqDto.allBuyCnt())
                        .totalPrice(reqDto.totalPrice())
                        .expireDatetime(LocalDateTime.now().plusHours(3))
                .build());

        // 주문서 품목 등록
        for(CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto dto : reqDto.orderItemList()) {
            orderItemSheetRepository.save(OrderItemSheet.builder()
                            .orderSheet(orderSheet)
                            .productId(dto.productId())
                            .productName(dto.productName())
                            .productOptionId(dto.productOptionId())
                            .productOptionName(dto.productOptionName())
                            .productPrice(dto.productPrice())
                            .buyCnt(dto.buyCnt())
                            .status(OrderEnums.Status.WAIT)
                    .build());
        }
        
        // 주문서 조회
        OrderSheet orderSheetInfo = orderSheetRepository.findByOrderSheetId(orderSheet.getOrderSheetId());

        // 주문서 품목 조회
        List<OrderItemSheet> orderItemSheetList = orderItemSheetRepository.findByOrderSheetId(orderSheetInfo.getOrderSheetId());

        List<CreateOrderSheetApiResDto.CreateOrderItemSheetApiResDto> list = orderItemSheetList.stream()
                        .map(CreateOrderSheetApiResDto.CreateOrderItemSheetApiResDto::from)
                                .toList();

        return CreateOrderSheetApiResDto.from(orderSheetInfo, list);
    }
}
