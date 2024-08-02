package com.hhplus.ecommerce.service.product;

import com.hhplus.ecommerce.controller.product.dto.AddProductApiReqDto;
import com.hhplus.ecommerce.controller.product.dto.FindProductListApiResDto;
import com.hhplus.ecommerce.controller.product.dto.PutProductApiReqDto;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;

import java.util.List;

public interface ProductService {
    List<FindProductListApiResDto> findProductList();
    Product findProductByProductId(Long productId);
    ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId);
    List<ProductOption> findProductOptionListByProductId(Long productId);
    Long addProduct(AddProductApiReqDto reqDto);
    Long putProduct(Long productId, PutProductApiReqDto reqDto);
    boolean delProduct(Long productId);
}
