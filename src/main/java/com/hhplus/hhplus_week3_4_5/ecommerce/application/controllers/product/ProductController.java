package com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product.dtos.AddProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product.dtos.GetProductApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product.dtos.GetProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product.dtos.PutProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "/products", description = "상품 API")
@RestController
@RequiredArgsConstructor
public class ProductController {

    @Operation(summary = "상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetProductApiResDto.class)))
    @GetMapping(value = "/products/{productId}")
    public GetProductApiResDto product(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId) {
        List<GetProductApiResDto.GetProductOptionApiResDto.GetProductOptionValueApiResDto> list = new ArrayList<>();
        list.add(new GetProductApiResDto.GetProductOptionApiResDto.GetProductOptionValueApiResDto(1L, "빨강", BigDecimal.valueOf(200)));

        GetProductApiResDto.GetProductOptionApiResDto option = new GetProductApiResDto.GetProductOptionApiResDto(ProductEnums.OptionType.BASIC, "색깔", list);
        return new GetProductApiResDto(1L, "A 운동화", ProductEnums.Type.OPTION, BigDecimal.valueOf(1000), option);
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GetProductRankingApiResDto.class))
    )})    @GetMapping(value = "/products/ranking")
    public List<GetProductRankingApiResDto> productRanking(@RequestParam(name = "type") @Schema(description = "상품 랭킹 타입 Enum") ProductEnums.Ranking type) {
        List<GetProductRankingApiResDto> list = new ArrayList<>();
        list.add(new GetProductRankingApiResDto(1L, "A 운동화"));
        list.add(new GetProductRankingApiResDto(2L, "B 시계"));
        list.add(new GetProductRankingApiResDto(3L, "C 가방"));
        return list;
    }

    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/products")
    public Long addProduct(@RequestBody @Valid AddProductApiReqDto reqDto) {
        return 1L;
    }

    @Operation(summary = "상품 수정")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PatchMapping(value = "/products/{productId}")
    public boolean putProduct(@PathVariable(name = "productId") @Schema(description = "상품 ID") @NotNull Long productId,
                              @RequestBody @Valid PutProductApiReqDto reqDto) {
        return true;
    }

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
