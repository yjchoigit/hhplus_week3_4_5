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

    public OrderSheet add_order_sheet(Buyer buyer, int number){

        OrderSheet orderSheet = orderSheetRepository.save(new OrderSheet(buyer.getBuyerId(),
                buyer.getName(), 1000 * number, number, LocalDateTime.now().plusHours(1), List.of(String.valueOf(number))));

        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        for(ProductOption option : productOptionList){
            orderItemSheetRepository.save(new OrderItemSheet(orderSheet, product.getProductId(), product.getName(),
                    option.getProductOptionId(), option.optionStr(),
                    1000, number, OrderEnums.Status.WAIT));
        }

        return orderSheet;
    }
}
