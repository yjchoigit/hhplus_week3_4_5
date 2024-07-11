package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
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
    private ProductFixture productFixture;

    public void 주문_등록(Buyer buyer, int number){

        Order order = orderRepository.save(new Order("20240712000"+number, buyer.getBuyerId(),
                buyer.getName(), number, 1000 * number));

        Product product = productFixture.사용가능_상품_등록();
        List<ProductOption> options = productFixture.사용가능_상품옵션_등록(product);

        for(ProductOption option : options){
            orderItemRepository.save(new OrderItem(order, product.getProductId(), product.getName(),
                    option.getProductOptionId(), option.getOptionName()+"/"+option.getOptionValue(),
                    1000, number, OrderEnums.Status.DEPOSIT_COMPLETE));
        }
    }
}
