package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
}
