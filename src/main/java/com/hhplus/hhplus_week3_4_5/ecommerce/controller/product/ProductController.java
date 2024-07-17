package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.reponse.dto.ResponseDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.reponse.util.ResponseUtil;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.*;
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
    )})@GetMapping(value = "/products")
    public ResponseDto findProductList() {
        return ResponseUtil.success(productService.findProductList());
    }

    @Operation(summary = "상품 상세 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindProductApiResDto.class)))
    @GetMapping(value = "/products/{productId}")
    public FindProductApiResDto findProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        return productStockFacade.findProduct(productId);
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindProductRankingApiResDto.class))
    )})    @GetMapping(value = "/products/ranking")
    public List<FindProductRankingApiResDto> findProductRanking() {
        return productOrderFacade.findProductRanking();
    }


    @Operation(summary = "상ㄴ품 등록")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/products")
    public Long addProduct(@RequestBody @Valid AddProductApiReqDto reqDto) {
        return productService.addProduct(reqDto);
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
