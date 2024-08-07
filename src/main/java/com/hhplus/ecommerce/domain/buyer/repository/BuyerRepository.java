package com.hhplus.ecommerce.domain.buyer.repository;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;

public interface BuyerRepository {
    Buyer save(Buyer buyer);
}
