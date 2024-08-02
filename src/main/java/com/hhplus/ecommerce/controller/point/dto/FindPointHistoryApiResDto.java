package com.hhplus.ecommerce.controller.point.dto;

import com.hhplus.ecommerce.domain.point.PointEnums;
import com.hhplus.ecommerce.domain.point.entity.PointHistory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

public record FindPointHistoryApiResDto(
        @Schema(description = "잔액 내역 ID")
        Long pointHistoryId,
        @Schema(description = "잔액 타입 Enum")
        PointEnums.Type type,
        @Schema(description = "충전/사용한 잔액")
        int usePoint,
        @Schema(description = "잔액 내역 등록일")
        LocalDateTime createDatetime
) implements Serializable {
        public static FindPointHistoryApiResDto from(PointHistory pointHistory){
                return new FindPointHistoryApiResDto(pointHistory.getPointHistoryId(), pointHistory.getType(), pointHistory.getUsePoint(), pointHistory.getCreateDatetime());
        }
}