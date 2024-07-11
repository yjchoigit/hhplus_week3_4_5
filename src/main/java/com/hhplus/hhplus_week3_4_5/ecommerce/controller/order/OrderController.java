package com.hhplus.hhplus_week3_4_5.ecommerce.controller.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.FindOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.facade.order.OrderPaymentFacade;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.order.OrderService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.order.OrderSheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "/orders", description = "주문 API")
@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderPaymentFacade orderPaymentFacade;
    private final OrderSheetService orderSheetService;
    private final OrderService orderService;

    @Operation(summary = "주문서 생성")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/orders/sheet")
    public Long createOrderSheet(@RequestBody @Valid CreateOrderSheetApiReqDto reqDto){
        return orderSheetService.createOrderSheet(reqDto);
    }

    @Operation(summary = "주문 진행")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/orders")
    public Long createOrder(@RequestBody @Valid CreateOrderApiReqDto reqDto){
        return orderPaymentFacade.createOrder(reqDto);
    }
    
    @Operation(summary = "주문 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindOrderApiResDto.class)))
    @GetMapping(value = "/orders/{buyerId}")
    public FindOrderApiResDto findOrder(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                                        @RequestParam(name = "orderId") @Schema(description = "주문 ID") @NotNull Long orderId){
        return orderService.findOrder(buyerId, orderId);
    }
}
