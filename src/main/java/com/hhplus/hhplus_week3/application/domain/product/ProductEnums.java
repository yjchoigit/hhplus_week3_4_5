package com.hhplus.hhplus_week3.application.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ProductEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Type {
        SINGLE, OPTION
    }

    @Getter
    @RequiredArgsConstructor
    public enum OptionType {
        BASIC, ADD
    }

    @Getter
    @RequiredArgsConstructor
    public enum Ranking {
        POPULAR, RECENT, HIGH, LOW
    }
}
