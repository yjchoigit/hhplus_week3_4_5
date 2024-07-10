package com.hhplus.hhplus_week3_4_5.ecommerce.controller.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.point.dto.GetPointHistoryApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.PointEnums;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "/points", description = "잔액 API")
@RestController
@RequiredArgsConstructor
public class PointController {

    @Operation(summary = "잔액 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    @GetMapping(value = "/points/{buyerId}")
    public int point(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId) {
        return 100;
    }

    @Operation(summary = "잔액 충전")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @PostMapping(value = "/points/{buyerId}")
    public boolean chargePoint(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                               @RequestParam(name = "point") @Schema(description = "충전할 포인트") int point) {
        return true;
    }

    @Operation(summary = "잔액 내역 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GetPointHistoryApiResDto.class))
    )})
    @GetMapping(value = "/points/history/{buyerId}")
    public List<GetPointHistoryApiResDto> pointHistory(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId) {
        List<GetPointHistoryApiResDto> list = new ArrayList<>();
        list.add(new GetPointHistoryApiResDto(1L, PointEnums.Type.CHARGE, 2000, LocalDateTime.now()));
        return list;
    }
}
