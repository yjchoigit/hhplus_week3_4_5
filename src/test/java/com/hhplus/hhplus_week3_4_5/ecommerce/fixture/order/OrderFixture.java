package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFixture {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;
    @Autowired
    private ProductFixture productFixture;

    public Order add_order_wait(Long orderSheetId, Buyer buyer, int number){

        Order order = orderRepository.save(new Order(orderSheetId, "20240712000"+number, buyer.getBuyerId(),
                buyer.getName(), number, 1000 * number));

        Product product = productFixture.add_usable_product();
        List<ProductOption> options = productFixture.add_usable_product_option(product);

        for(ProductOption option : options){
            orderItemRepository.save(new OrderItem(order, product.getProductId(), product.getName(),
                    option.getProductOptionId(), option.optionStr(),
                    1000, number, OrderEnums.Status.WAIT));
        }

        orderPaymentRepository.save(new OrderPayment(order, order.getTotalPrice(), OrderEnums.PaymentStatus.WAIT));

        return order;
    }

    public Order add_order_pay_complete(Long orderSheetId, Buyer buyer, int number){

        Order order = orderRepository.save(new Order(orderSheetId, "20240712000"+number, buyer.getBuyerId(),
                buyer.getName(), number, 1000 * number));

        Product product = productFixture.add_usable_product();
        List<ProductOption> options = productFixture.add_usable_product_option(product);

        for(ProductOption option : options){
            orderItemRepository.save(new OrderItem(order, product.getProductId(), product.getName(),
                    option.getProductOptionId(), option.optionStr(),
                    1000, number, OrderEnums.Status.DEPOSIT_COMPLETE));
        }

        orderPaymentRepository.save(new OrderPayment(order, order.getTotalPrice(), OrderEnums.PaymentStatus.PAY_COMPLETE));

        return order;
    }
}
