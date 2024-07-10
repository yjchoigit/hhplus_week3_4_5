package com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerJpaRepository extends JpaRepository<Buyer, Long> {
}
