package com.hhplus.hhplus_week3_4_5.ecommerce.controller.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.util.ResponseUtil;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.point.dto.FindPointHistoryApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.point.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/points", description = "잔액 API")
@RestController
@RequiredArgsConstructor
@Validated
public class PointController {

    private final PointService pointService;

    @Operation(summary = "잔액 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    @GetMapping(value = "/points/{buyerId}")
    public ResponseDto<Integer> findPoint(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId) {
        return ResponseUtil.success(pointService.findPoint(buyerId));
    }

    @Operation(summary = "잔액 충전")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PostMapping(value = "/points/{buyerId}")
    public ResponseDto<Void> chargePoint(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                               @RequestParam(name = "point") @Schema(description = "충전할 포인트") int point) {
        boolean successYn = pointService.chargePoint(buyerId, point);
        if(!successYn){
            return ResponseUtil.failure();
        }
        return ResponseUtil.success();
    }

    @Operation(summary = "잔액 내역 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindPointHistoryApiResDto.class))
    )})
    @GetMapping(value = "/points/history/{buyerId}")
    public ResponseDto<List<FindPointHistoryApiResDto>> findPointHistoryList(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId) {
        return ResponseUtil.success(pointService.findPointHistoryList(buyerId));
    }
}
