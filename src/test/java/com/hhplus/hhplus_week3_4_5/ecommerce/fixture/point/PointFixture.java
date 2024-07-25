package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PointFixture {
    @Autowired
    private PointRepository pointRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Point add_point(Long buyerId, int allPoint){
        return pointRepository.save(new Point(buyerId, allPoint));
    }
}

