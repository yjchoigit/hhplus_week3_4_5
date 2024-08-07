package com.hhplus.ecommerce.service.product;

import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.service.product.dto.AddProductReqDto;
import com.hhplus.ecommerce.service.product.dto.PutProductReqDto;

import java.util.List;

public interface ProductService {
    // 상품 목록 조회
    List<Product> findProductList();
    
    // 상품 id로 상품 조회
    Product findProductByProductId(Long productId);
    
    // 상품 id, 상품 옵션 id로 상품 옵션 조회
    ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId);
    
    // 상품 id로 상품 옵션 목록 조회
    List<ProductOption> findProductOptionListByProductId(Long productId);
    
    // 상품 등록
    Product addProduct(AddProductReqDto reqDto);
    
    // 상품 수정
    Product putProduct(Long productId, PutProductReqDto reqDto);
    
    // 상품 삭제
    void delProduct(Long productId);
}
