package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemSheetRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderSheetRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderSheetFixture {
    @Autowired
    private OrderSheetRepository orderSheetRepository;
    @Autowired
    private OrderItemSheetRepository orderItemSheetRepository;
    @Autowired
    private ProductFixture productFixture;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderSheet add_order_sheet(Buyer buyer, int number){

        OrderSheet orderSheet = orderSheetRepository.save(new OrderSheet(buyer.getBuyerId(),
                buyer.getName(), 1000 * number, number, LocalDateTime.now().plusHours(1), List.of(String.valueOf(number))));

        return orderSheet;
    }
}
