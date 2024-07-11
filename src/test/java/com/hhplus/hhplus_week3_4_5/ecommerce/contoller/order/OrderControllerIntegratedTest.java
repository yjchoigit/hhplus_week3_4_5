package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.contoller.Setting;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point.PointFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerIntegratedTest extends Setting {
    private static final String PATH = "/orders";

    @Autowired
    private BuyerFixture buyerFixture;

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private PointFixture pointFixture;

    @Test
    @DisplayName("주문 진행 성공")
    void createOrder_success(){
        // given
        Buyer buyer = buyerFixture.회원_등록();
        Point point = pointFixture.잔액_등록(buyer.getBuyerId(), 10000);

        Product product = productFixture.사용가능_상품_등록();
        List<ProductOption> productOptionList = productFixture.사용가능_상품옵션_등록(product);
        for(ProductOption option : productOptionList){
            productFixture.상품재고_등록(product, option, 100);
        }

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertEquals((Long) response.jsonPath().get(), 1L);
    }

    @Test
    @DisplayName("주문 진행 실패 - 사용하지 않는 상품 정보일 때")
    void createOrder_product_valid_fail(){
        // given
        Buyer buyer = buyerFixture.회원_등록();
        Point point = pointFixture.잔액_등록(buyer.getBuyerId(), 10000);

        Product product = productFixture.사용불가능_상품_등록();
        List<ProductOption> productOptionList = productFixture.사용가능_상품옵션_등록(product);
        for(ProductOption option : productOptionList){
            productFixture.상품재고_등록(product, option, 100);
        }

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("주문 진행 실패 - 재고가 부족할 때")
    void createOrder_stock_valid_fail(){
        // given
        Buyer buyer = buyerFixture.회원_등록();
        Point point = pointFixture.잔액_등록(buyer.getBuyerId(), 10000);

        Product product = productFixture.사용가능_상품_등록();
        List<ProductOption> productOptionList = productFixture.사용가능_상품옵션_등록(product);
        for(ProductOption option : productOptionList){
            productFixture.상품재고_등록(product, option, 1);
        }

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("주문 진행 실패 - 잔액이 부족할 때")
    void createOrder_point_valid_fail(){
        // given
        Buyer buyer = buyerFixture.회원_등록();
        Point point = pointFixture.잔액_등록(buyer.getBuyerId(), 100);

        Product product = productFixture.사용가능_상품_등록();
        List<ProductOption> productOptionList = productFixture.사용가능_상품옵션_등록(product);
        for(ProductOption option : productOptionList){
            productFixture.상품재고_등록(product, option, 100);
        }

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
