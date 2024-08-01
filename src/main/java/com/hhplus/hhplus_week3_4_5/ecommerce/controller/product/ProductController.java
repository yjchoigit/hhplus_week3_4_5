package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.config.cache.CacheConstants;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.util.ResponseUtil;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.*;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.facade.product.ProductOrderFacade;
import com.hhplus.hhplus_week3_4_5.ecommerce.facade.product.ProductStockFacade;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/products", description = "상품 API")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductStockFacade productStockFacade;
    private final ProductOrderFacade productOrderFacade;
    private final ProductService productService;

    @Operation(summary = "상품 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindProductListApiResDto.class))
    )})
    @GetMapping(value = "/products")
    public ResponseDto<List<FindProductListApiResDto>> findProductList() {
        return ResponseUtil.success(productService.findProductList());
    }

    @Operation(summary = "상품 상세 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindProductApiResDto.class)))
    @GetMapping(value = "/products/{productId}")
    public ResponseDto<FindProductApiResDto> findProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        return ResponseUtil.success(productStockFacade.findProduct(productId));
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindProductRankingApiResDto.class))
    )})    @GetMapping(value = "/products/ranking/{rankingType}")
    public ResponseDto<List<FindProductRankingApiResDto>> findProductRanking(@PathVariable(name = "rankingType") @Schema(description = "기간 타입") ProductEnums.Ranking rankingType) {
        return ResponseUtil.success(productOrderFacade.findProductRanking(rankingType));
    }

    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/products")
    public ResponseDto<Long> addProduct(@RequestBody @Valid AddProductApiReqDto reqDto) {
        Long result = productService.addProduct(reqDto);
        if(result == null) {
            ResponseUtil.failure(result);
        }
        return ResponseUtil.success(result);
    }

    @Operation(summary = "상품 수정")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PatchMapping(value = "/products/{productId}")
    public ResponseDto<Long> putProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId,
                              @RequestBody @Valid PutProductApiReqDto reqDto) {
        Long result = productService.putProduct(productId, reqDto);
        if(result == null) {
            ResponseUtil.failure(result);
        }
        return ResponseUtil.success(result);
    }

    @Operation(summary = "상품 삭제")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping(value = "/products/{productId}")
    public ResponseDto<Void> delProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        boolean successYn = productService.delProduct(productId);
        if(!successYn){
            return ResponseUtil.failure();
        }
        return ResponseUtil.success();
    }
}
