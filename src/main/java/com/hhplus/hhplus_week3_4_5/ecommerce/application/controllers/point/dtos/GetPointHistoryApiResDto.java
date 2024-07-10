package com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.point.dtos;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.PointEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetPointHistoryApiResDto(
        @Schema(description = "잔액 내역 ID")
        Long pointHistoryId,
        @Schema(description = "잔액 타입 Enum")
        PointEnums.Type type,
        @Schema(description = "충전/사용한 잔액")
        int point,
        @Schema(description = "잔액 내역 등록일")
        LocalDateTime createDatetime
) implements Serializable {
}