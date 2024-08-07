package com.hhplus.ecommerce.contoller.product;

import com.hhplus.ecommerce.base.config.cache.CacheConstants;
import com.hhplus.ecommerce.base.config.jwt.JwtTokenTestUtil;
import com.hhplus.ecommerce.base.setting.Setting;
import com.hhplus.ecommerce.controller.product.dto.FindProductApiResDto;
import com.hhplus.ecommerce.controller.product.dto.FindProductListApiResDto;
import com.hhplus.ecommerce.controller.product.dto.FindProductRankingApiResDto;
import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.ecommerce.fixture.order.OrderFixture;
import com.hhplus.ecommerce.fixture.product.ProductFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerCacheIntegratedTest extends Setting {
    private static final String PATH = "/products";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private OrderFixture orderFixture;

    @Autowired
    private BuyerFixture buyerFixture;

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
    @DisplayName("상품 목록 조회 - 캐시 적용 확인")
    void findProductList_cacheable_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        List<FindProductListApiResDto> initialData = findProductListInitialData();

        // when: 데이터베이스에 데이터 추가 또는 수정
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // then: 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH, token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 이전 데이터와 같은지 확인
        List<FindProductListApiResDto> updatedData = freshResponse.jsonPath().getList("data", FindProductListApiResDto.class);
        assertThat(updatedData).isEqualTo(initialData);
        assertThat(updatedData).isNotEmpty();
    }

    @Test
    @DisplayName("상품 목록 조회 - 캐시가 만료된 후 최신 데이터 조회")
    void findProductList_cacheable_expire_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        List<FindProductListApiResDto> initialData = findProductListInitialData();

        // when: 데이터베이스에 데이터 추가 또는 수정
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // 캐시 무효화
        Cache cache = cacheManager.getCache(CacheConstants.ProductGroup.FIND_PRODUCT_LIST);
        if (cache != null) {
            cache.clear();
        }

        // then: 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH, token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 최신 데이터가 반환되었는지 확인
        List<FindProductListApiResDto> updatedData = freshResponse.jsonPath().getList("data", FindProductListApiResDto.class);
        assertThat(updatedData).isNotEqualTo(initialData);
        assertThat(updatedData).isNotEmpty();
    }

    @Test
    @DisplayName("상품 상세 조회 - 캐시 적용 확인")
    void findProduct_cacheable_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        // given
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // API 호출을 통해 초기 데이터 조회
        ExtractableResponse<Response> response = get(PATH + "/" + product.getProductId(), token);
        assertThat(response.statusCode()).isEqualTo(200);
        FindProductApiResDto initialData = response.jsonPath().getObject("data", FindProductApiResDto.class);

        // when: 데이터베이스에 데이터 추가 또는 수정
        product.update(Product.builder()
                .price(100).build());

        // then: 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH+ "/" + product.getProductId(), token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 이전 데이터와 같은지 확인
        FindProductApiResDto updatedData = freshResponse.jsonPath().getObject("data", FindProductApiResDto.class);
        assertThat(updatedData).isNotEqualTo(initialData);
        assertThat(updatedData).isNotNull();
    }

    @Test
    @DisplayName("상품 상세 조회 - 캐시가 만료된 후 최신 데이터 조회")
    void findProduct_cacheable_expire_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        // given
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // API 호출을 통해 초기 데이터 조회
        ExtractableResponse<Response> response = get(PATH + "/" + product.getProductId(), token);
        assertThat(response.statusCode()).isEqualTo(200);
        FindProductApiResDto initialData = response.jsonPath().getObject("data", FindProductApiResDto.class);

        // when: 데이터베이스에 데이터 추가 또는 수정
        product.update(Product.builder()
                .price(100).build());

        // 캐시 무효화
        Cache cache = cacheManager.getCache(CacheConstants.ProductGroup.FIND_PRODUCT);
        if (cache != null) {
            cache.evict(product.getProductId()); // 특정 키의 캐시 항목만 무효화
        }

        // then: API 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH+ "/" + product.getProductId(), token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 이전 데이터와 같은지 확인
        FindProductApiResDto updatedData = freshResponse.jsonPath().getObject("data", FindProductApiResDto.class);
        assertThat(updatedData).isEqualTo(initialData);
        assertThat(updatedData).isNotNull();
    }

    @Test
    @DisplayName("상위 상품 조회 - 캐시 적용 확인")
    void findProductRanking_cacheable_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        orderFixture.add_order_pay_complete(1L, buyer, 10);
        orderFixture.add_order_pay_complete(2L, buyer, 20);
        orderFixture.add_order_pay_complete(3L, buyer, 30);
        orderFixture.add_order_pay_complete(4L, buyer, 40);
        orderFixture.add_order_pay_complete(5L, buyer, 50);
        orderFixture.add_order_pay_complete(6L, buyer, 60);
        orderFixture.add_order_pay_complete(7L, buyer, 70);

        // API 호출을 통해 초기 데이터 조회
        ExtractableResponse<Response> response = get(PATH + "/ranking/" + ProductEnums.Ranking.THREE_DAY, token);
        assertThat(response.statusCode()).isEqualTo(200);
        List<FindProductRankingApiResDto> initialData = response.jsonPath().getList("data", FindProductRankingApiResDto.class);

        // when: 데이터베이스에 데이터 추가 또는 수정
        orderFixture.add_order_pay_complete(8L, buyer, 80);
        orderFixture.add_order_pay_complete(9L, buyer, 90);
        orderFixture.add_order_pay_complete(10L, buyer, 100);

        // then: 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH + "/ranking/" + ProductEnums.Ranking.THREE_DAY, token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 이전 데이터와 같은지 확인
        List<FindProductRankingApiResDto> updatedData = freshResponse.jsonPath().getList("data", FindProductRankingApiResDto.class);
        assertThat(updatedData).isEqualTo(initialData);
        assertThat(updatedData).isNotEmpty();
    }

    @Test
    @DisplayName("상위 상품 조회 - 캐시가 만료된 후 최신 데이터 조회")
    void findProductRanking_cacheable_expire_success() {
        // given: 초기 데이터 상태를 기록하고 API 호출
        orderFixture.add_order_pay_complete(1L, buyer, 10);
        orderFixture.add_order_pay_complete(2L, buyer, 20);
        orderFixture.add_order_pay_complete(3L, buyer, 30);
        orderFixture.add_order_pay_complete(4L, buyer, 40);
        orderFixture.add_order_pay_complete(5L, buyer, 50);
        orderFixture.add_order_pay_complete(6L, buyer, 60);
        orderFixture.add_order_pay_complete(7L, buyer, 70);

        // API 호출을 통해 초기 데이터 조회
        ExtractableResponse<Response> response = get(PATH + "/ranking/" + ProductEnums.Ranking.THREE_DAY, token);
        assertThat(response.statusCode()).isEqualTo(200);
        List<FindProductRankingApiResDto> initialData = response.jsonPath().getList("data", FindProductRankingApiResDto.class);

        // when: 데이터베이스에 데이터 추가 또는 수정
        orderFixture.add_order_pay_complete(8L, buyer, 80);
        orderFixture.add_order_pay_complete(9L, buyer, 90);
        orderFixture.add_order_pay_complete(10L, buyer, 100);

        // 캐시 무효화
        Cache cache = cacheManager.getCache(CacheConstants.ProductGroup.FIND_PRODUCT_RANKING);
        if (cache != null) {
            cache.evict(ProductEnums.Ranking.THREE_DAY.name()); // 특정 키의 캐시 항목만 무효화
        }

        // then: 재호출 및 데이터 검증
        ExtractableResponse<Response> freshResponse = get(PATH + "/ranking/" + ProductEnums.Ranking.THREE_DAY, token);
        assertThat(freshResponse.statusCode()).isEqualTo(200);

        // 이전 데이터와 같은지 확인
        List<FindProductRankingApiResDto> updatedData = freshResponse.jsonPath().getList("data", FindProductRankingApiResDto.class);
        assertThat(updatedData).isNotEqualTo(initialData);
        assertThat(updatedData).isNotEmpty();
    }

    private List<FindProductListApiResDto> findProductListInitialData() {
        // given
        Product product = productFixture.add_usable_product();
        List<ProductOption> productOptionList = productFixture.add_usable_product_option(product);
        for(ProductOption option : productOptionList){
            productFixture.add_product_stock(product, option, 100);
        }

        // API 호출을 통해 초기 데이터 조회
        ExtractableResponse<Response> response = get(PATH, token);
        assertThat(response.statusCode()).isEqualTo(200);
        return response.jsonPath().getList("data", FindProductListApiResDto.class);
    }

}
