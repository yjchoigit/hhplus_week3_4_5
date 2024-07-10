package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.buyer;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entities.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerJpaRepository extends JpaRepository<Buyer, Long> {
}
