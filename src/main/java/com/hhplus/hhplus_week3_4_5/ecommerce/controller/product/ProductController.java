package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.AddProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.GetProductApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.GetProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.PutProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.facade.product.ProductStockFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "/products", description = "상품 API")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductStockFacade productStockFacade;

    @Operation(summary = "상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetProductApiResDto.class)))
    @GetMapping(value = "/products/{productId}")
    public GetProductApiResDto product(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        return productStockFacade.findProductInfo(productId);
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GetProductRankingApiResDto.class))
    )})    @GetMapping(value = "/products/ranking")
    public List<GetProductRankingApiResDto> productRanking() {
        List<GetProductRankingApiResDto> list = new ArrayList<>();
        list.add(new GetProductRankingApiResDto(1L, "A 운동화"));
        list.add(new GetProductRankingApiResDto(2L, "B 시계"));
        list.add(new GetProductRankingApiResDto(3L, "C 가방"));
        return list;
    }
    
    // TODO 상품 등록 API
    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/products")
    public Long addProduct(@RequestBody @Valid AddProductApiReqDto reqDto) {
        return 1L;
    }

    // TODO 상품 수정 API
    @Operation(summary = "상품 수정")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PatchMapping(value = "/products/{productId}")
    public boolean putProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId,
                              @RequestBody @Valid PutProductApiReqDto reqDto) {
        return true;
    }

    // TODO 상품 삭제 API
    @Operation(summary = "상품 삭제")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping(value = "/products/{productId}")
    public boolean deleteProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        return true;
    }

    // 상품 옵션 추가 API

    // 상품 옵션 수정 API
    
    // 상품 옵션 삭제 API
}
