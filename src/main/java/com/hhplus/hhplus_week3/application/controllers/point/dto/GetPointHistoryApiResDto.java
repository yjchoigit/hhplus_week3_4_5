package com.hhplus.hhplus_week3.application.controllers.point.dto;

import com.hhplus.hhplus_week3.application.domain.point.PointEnums;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetPointHistoryApiResDto(
        Long pointHistoryId,
        PointEnums.Type type,
        int point,
        LocalDateTime createDatetime
) implements Serializable {
}