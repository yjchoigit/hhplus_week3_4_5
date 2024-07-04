package com.hhplus.hhplus_week3.application.controllers.product;

import com.hhplus.hhplus_week3.application.controllers.product.dto.AddProductApiReqDto;
import com.hhplus.hhplus_week3.application.controllers.product.dto.GetProductApiResDto;
import com.hhplus.hhplus_week3.application.controllers.product.dto.GetProductRankingApiResDto;
import com.hhplus.hhplus_week3.application.controllers.product.dto.PutProductApiReqDto;
import com.hhplus.hhplus_week3.application.domain.product.ProductEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    // 상품 조회 API
    @GetMapping(value = "/products/{productId}")
    public GetProductApiResDto product(@PathVariable(name = "productId") Long productId) {
        List<GetProductApiResDto.GetProductOptionApiResDto.GetProductOptionValueApiResDto> list = new ArrayList<>();
        list.add(new GetProductApiResDto.GetProductOptionApiResDto.GetProductOptionValueApiResDto(1L, "빨강", BigDecimal.valueOf(200)));

        GetProductApiResDto.GetProductOptionApiResDto option = new GetProductApiResDto.GetProductOptionApiResDto(ProductEnums.OptionType.BASIC, "색깔", list);
        return new GetProductApiResDto(1L, "A 운동화", ProductEnums.Type.OPTION, BigDecimal.valueOf(1000), option);
    }

    // 상위 상품 조회 API
    @GetMapping(value = "/products/ranking")
    public List<GetProductRankingApiResDto> productRanking(@RequestParam(name = "type") ProductEnums.Ranking type) {
        List<GetProductRankingApiResDto> list = new ArrayList<>();
        list.add(new GetProductRankingApiResDto(1L, "A 운동화"));
        list.add(new GetProductRankingApiResDto(2L, "B 시계"));
        list.add(new GetProductRankingApiResDto(3L, "C 가방"));
        return list;
    }

    // 상품 등록 API
    @PostMapping(value = "/products")
    public Long addProduct(@RequestBody AddProductApiReqDto reqDto) {
        return 1L;
    }

    // 상품 수정 API
    @PatchMapping(value = "/products/{productId}")
    public boolean putProduct(@PathVariable(name = "productId") Long productId, @RequestBody PutProductApiReqDto reqDto) {
        return true;
    }

    // 상품 삭제 API
    @DeleteMapping(value = "/products/{productId}")
    public boolean deleteProduct(@PathVariable(name = "productId") Long productId) {
        return true;
    }

    // 상품 옵션 추가 API

    // 상품 옵션 수정 API
    
    // 상품 옵션 삭제 API
}
