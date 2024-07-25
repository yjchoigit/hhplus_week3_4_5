package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.Setting;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.config.jwt.JwtTokenTestUtil;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.BaseEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.PaymentOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order.OrderFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order.OrderSheetFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point.PointFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private OrderSheetFixture orderSheetFixture;

    @Autowired
    private OrderFixture orderFixture;

    @Autowired
    private JwtTokenTestUtil jwtTokenUtil;

    private Buyer buyer;
    private String token;

    @BeforeEach
    void setUp(){
        buyer = buyerFixture.add_buyer();
        token = jwtTokenUtil.testGenerateToken(buyer.getBuyerId());
    }

    @Test
    @DisplayName("주문서 생성 성공")
    void createOrderSheet_success(){
        // given
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        List<CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto> items = List.of(CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());

        CreateOrderSheetApiReqDto reqDto =  CreateOrderSheetApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .cartIdList(List.of(1L,2L,3L))
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH + "/sheet", reqDto, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.SUCCESS.getCode());
    }

    @Test
    @DisplayName("주문 진행 성공")
    void createOrder_success(){
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);
        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.jsonPath().getObject("data", Long.class), 1L);
    }

    @Test
    @DisplayName("주문 진행 실패 - 사용하지 않는 상품 정보일 때")
    void createOrder_product_valid_fail(){
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto, token);

        // then
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }

    @Test
    @DisplayName("주문 진행 실패 - 재고가 부족할 때")
    void createOrder_stock_valid_fail(){
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);

        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();

        // when
        ExtractableResponse<Response> response = post(PATH, reqDto, token);

        // then
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }


    @Test
    @DisplayName("주문 결제 진행 성공")
    void paymentOrder_success(){
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        Order order = orderFixture.add_order_wait(orderSheet.getOrderSheetId(), buyer, 10);

        PaymentOrderApiReqDto reqDto = new PaymentOrderApiReqDto(buyer.getBuyerId(), order.getOrderId());

        // when
        ExtractableResponse<Response> response = post(PATH + "/payment", reqDto, token);

        // then
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.SUCCESS.getCode());
    }

    @Test
    @DisplayName("주문 결제 진행 실패 - 이미 결제 완료된 주문")
    void paymentOrder_status_fail(){
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        Order order = orderFixture.add_order_pay_complete(orderSheet.getOrderSheetId(), buyer, 10);

        PaymentOrderApiReqDto reqDto = new PaymentOrderApiReqDto(buyer.getBuyerId(), order.getOrderId());

        // when
        ExtractableResponse<Response> response = post(PATH + "/payment", reqDto, token);

        // then
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }

    @Test
    @DisplayName("주문 결제 진행 실패 - 잔액이 부족할 때")
    void paymentOrder_point_valid_fail(){
        // given
        pointFixture.add_point(buyer.getBuyerId(), 10);
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        Order order = orderFixture.add_order_pay_complete(orderSheet.getOrderSheetId(), buyer, 10);

        PaymentOrderApiReqDto reqDto = new PaymentOrderApiReqDto(buyer.getBuyerId(), order.getOrderId());

        // when
        ExtractableResponse<Response> response = post(PATH + "/payment", reqDto, token);

        // then
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }
}
