package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.Setting;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.config.jwt.JwtTokenTestUtil;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.BaseEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order.OrderFixture;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductControllerIntegratedTest extends Setting {
    private static final String PATH = "/products";

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private OrderFixture orderFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    @Autowired
    private JwtTokenTestUtil jwtTokenUtil;

    @Test
    @DisplayName("상품 상세 조회 성공")
    void findProduct_success(){
        // given
        Buyer buyer = buyerFixture.add_buyer();
        String token = jwtTokenUtil.testGenerateToken(buyer.getBuyerId());

        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // when
        ExtractableResponse<Response> response = get(PATH + "/" + product.getProductId(), token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.jsonPath().getObject("data", FindProductApiResDto.class));
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 상품 정보가 없을 때")
    void findProduct_no_product_info_fail(){
        // given
        Buyer buyer = buyerFixture.add_buyer();
        String token = jwtTokenUtil.testGenerateToken(buyer.getBuyerId());

        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // when
        ExtractableResponse<Response> response = get(PATH + "/" + 2L, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 상품 재고 정보가 없을 때")
    void findProduct_no_stock_info_fail(){
        // given
        Buyer buyer = buyerFixture.add_buyer();
        String token = jwtTokenUtil.testGenerateToken(buyer.getBuyerId());

        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);

        // when
        ExtractableResponse<Response> response = get(PATH + "/" + 1L, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.jsonPath().getObject("status", String.class), BaseEnums.ResponseStatus.FAILURE.getCode());
    }

    @Test
    @DisplayName("상위 상품 조회 성공")
    void findProductRanking_success(){
        // given
        Buyer buyer = buyerFixture.add_buyer();
        String token = jwtTokenUtil.testGenerateToken(buyer.getBuyerId());
        orderFixture.add_order(buyer, 10);
        orderFixture.add_order(buyer, 20);
        orderFixture.add_order(buyer, 30);
        orderFixture.add_order(buyer, 40);
        orderFixture.add_order(buyer, 50);
        orderFixture.add_order(buyer, 60);
        orderFixture.add_order(buyer, 70);

        // when
        ExtractableResponse<Response> response = get(PATH +"/ranking", token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ResponseDto<List<FindProductRankingApiResDto>> responseDto = response.as(ResponseDto.class);
        assertThat(responseDto.getData()).isNotEmpty();
    }
}
