package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BuyerFixture {
    @Autowired
    private BuyerRepository buyerRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Buyer add_buyer(){
        return buyerRepository.save(new Buyer("홍길동"));
    }
}
