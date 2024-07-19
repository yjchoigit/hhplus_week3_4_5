package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product;

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

    @Getter
    @RequiredArgsConstructor
    public enum StockType {
        SINGLE, OPTION, ADD
    }

    @Getter
    @RequiredArgsConstructor
    public enum Error {
        NO_PRODUCT("DELETE_PRODUCT","상품 정보가 없습니다."),
        DELETE_PRODUCT("DELETE_PRODUCT","삭제된 상품입니다."),
        UNUSABLE_PRODUCT("UNUSABLE_PRODUCT","사용하지 않는 상품입니다."),
        NO_PRODUCT_OPTION("NO_PRODUCT_OPTION","상품 옵션 정보가 없습니다."),
        UNUSABLE_PRODUCT_OPTION("UNUSABLE_PRODUCT_OPTION","사용하지 않는 상품 옵션입니다."),
        NO_PRODUCT_STOCK("NO_PRODUCT_STOCK","상품 재고 정보가 없습니다."),
        ZERO_PRODUCT_STOCK("ZERO_PRODUCT_STOCK","재고가 없습니다."),
        OUT_OF_PRODUCT_STOCK("OUT_OF_PRODUCT_STOCK","재고가 부족합니다."),

        ;
        private final String code;
        private final String message;
    }
}
