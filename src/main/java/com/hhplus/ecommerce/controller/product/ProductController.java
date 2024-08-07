package com.hhplus.ecommerce.controller.product;

import com.hhplus.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.ecommerce.base.exception.reponse.util.ResponseUtil;
import com.hhplus.ecommerce.controller.product.dto.*;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.facade.product.ProductOrderFacade;
import com.hhplus.ecommerce.facade.product.ProductStockFacade;
import com.hhplus.ecommerce.facade.product.dto.FindProductRankingResDto;
import com.hhplus.ecommerce.facade.product.dto.FindProductResDto;
import com.hhplus.ecommerce.service.product.ProductService;
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
    )})
    @GetMapping(value = "/products")
    public ResponseDto<List<FindProductListApiResDto>> findProductList() {
        List<Product> productList = productService.findProductList();
        return ResponseUtil.success(productList.stream().map(FindProductListApiResDto::from).toList());
    }

    @Operation(summary = "상품 상세 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindProductApiResDto.class)))
    @GetMapping(value = "/products/{productId}")
    public ResponseDto<FindProductApiResDto> findProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        FindProductResDto findProductResDto = productStockFacade.findProduct(productId);
        return ResponseUtil.success(FindProductApiResDto.from(findProductResDto));
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindProductRankingApiResDto.class))
    )})    @GetMapping(value = "/products/ranking/{rankingType}")
    public ResponseDto<List<FindProductRankingApiResDto>> findProductRanking(@PathVariable(name = "rankingType") @Schema(description = "기간 타입") ProductEnums.Ranking rankingType) {
        List<FindProductRankingResDto> productRankingList = productOrderFacade.findProductRanking(rankingType);
        return ResponseUtil.success(productRankingList.stream().map(FindProductRankingApiResDto::from).toList());
    }

    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/products")
    public ResponseDto<Long> addProduct(@RequestBody @Valid AddProductApiReqDto reqDto) {
        Product product = productService.addProduct(reqDto.request());
        if(product == null) {
            return ResponseUtil.failure();
        }
        return ResponseUtil.success(product.getProductId());
    }

    @Operation(summary = "상품 수정")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PatchMapping(value = "/products/{productId}")
    public ResponseDto<Long> putProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId,
                              @RequestBody @Valid PutProductApiReqDto reqDto) {
        Product product = productService.putProduct(productId, reqDto.request());
        if(product == null) {
            return ResponseUtil.failure();
        }
        return ResponseUtil.success(product.getProductId());
    }

    @Operation(summary = "상품 삭제")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping(value = "/products/{productId}")
    public ResponseDto<Void> delProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        productService.delProduct(productId);
        return ResponseUtil.success();
    }
}
