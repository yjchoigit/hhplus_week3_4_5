package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.*;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {
    List<FindProductListApiResDto> findProductList();
    Product findProductByProductId(Long productId);
    ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId);
    List<ProductOption> findProductOptionListByProductId(Long productId);
    Long addProduct(AddProductApiReqDto reqDto);
}
