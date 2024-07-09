package com.hhplus.hhplus_week3_4_5.application.controllers.order;

import com.hhplus.hhplus_week3_4_5.application.controllers.order.dto.GetOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.application.domain.payment.PaymentEnums;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "/orders", description = "주문 API")
@RestController
@RequiredArgsConstructor
public class OrderController {

    @Operation(summary = "주문 생성")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/orders/{buyerId}")
    public Long orders(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                       @RequestParam(name = "cartIdList") @Schema(description = "장바구니 ID 리스트") @NotNull List<Long> cartIdList){
        return 1L;
    }

    @Operation(summary = "주문 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetOrderApiResDto.class)))
    @GetMapping(value = "/orders/{buyerId}")
    public GetOrderApiResDto orders(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                                    @RequestParam(name = "orderId") @Schema(description = "주문 ID") @NotNull Long orderId){
        return null;
    }
}
