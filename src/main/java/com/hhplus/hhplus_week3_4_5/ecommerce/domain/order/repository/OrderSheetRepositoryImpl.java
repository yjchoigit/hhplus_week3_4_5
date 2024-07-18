package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class OrderSheetRepositoryImpl implements OrderSheetRepository {
    private final OrderSheetJpaRepository orderSheetJpaRepository;

    public OrderSheetRepositoryImpl(OrderSheetJpaRepository orderSheetJpaRepository) {
        this.orderSheetJpaRepository = orderSheetJpaRepository;
    }

    @Override
    public OrderSheet save(OrderSheet orderSheet) {
        return orderSheetJpaRepository.save(orderSheet);
    }

    @Override
    public void delete(OrderSheet orderSheet) {
        orderSheetJpaRepository.delete(orderSheet);
    }

    @Override
    public OrderSheet findByOrderSheetId(Long orderSheetId) {
        return orderSheetJpaRepository.findById(orderSheetId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public OrderSheet findByBuyerId(Long buyerId) {
        return orderSheetJpaRepository.findFirstByBuyerId(buyerId);
    }
}
