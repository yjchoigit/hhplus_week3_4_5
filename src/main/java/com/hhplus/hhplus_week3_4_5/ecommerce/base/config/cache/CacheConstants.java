package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProductGroup {
        public static final String FIND_PRODUCT_LIST = "FIND_PRODUCT_LIST";
        public static final String FIND_PRODUCT = "FIND_PRODUCT";
        public static final String FIND_PRODUCT_RANKING = "FIND_PRODUCT_RANKING";
    }

    public static List<String> allCacheList(){
        List<String> list = new ArrayList<>();
        Field[] declaredFields = ProductGroup.class.getDeclaredFields();
        for(Field field : declaredFields){
            list.add(field.getName());
        }
        return list;
    }
}
