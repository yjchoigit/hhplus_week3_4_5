package com.hhplus.ecommerce.fixture.order;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.ecommerce.domain.order.repository.OrderItemSheetRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderSheetRepository;
import com.hhplus.ecommerce.fixture.product.ProductFixture;
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
