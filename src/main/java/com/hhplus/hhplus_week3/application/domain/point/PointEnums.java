package com.hhplus.hhplus_week3.application.domain.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PointEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Type {
        USE, CHARGE
    }
}
