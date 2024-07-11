package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFixture {
    @Autowired
    private PointRepository pointRepository;

    public Point 잔액_등록(Long buyerId, int allPoint){
        return pointRepository.save(new Point(buyerId, allPoint));
    }
}

