package com.hhplus.hhplus_week3_4_5.application.controllers.payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "/payments", description = "결제 API")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    @Operation(summary = "결제 진행")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PostMapping(value = "/payments/{orderId}")
    public boolean payment(@PathVariable("orderId") @Schema(description = "주문 ID") @NotNull Long orderId) {
        return true;
    }

}
