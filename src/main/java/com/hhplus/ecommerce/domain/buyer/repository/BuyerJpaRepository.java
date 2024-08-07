package com.hhplus.ecommerce.domain.buyer.repository;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerJpaRepository extends JpaRepository<Buyer, Long> {
}
