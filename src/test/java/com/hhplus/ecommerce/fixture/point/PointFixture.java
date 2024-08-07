package com.hhplus.ecommerce.fixture.point;

import com.hhplus.ecommerce.domain.point.entity.Point;
import com.hhplus.ecommerce.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFixture {
    @Autowired
    private PointRepository pointRepository;

    public Point add_point(Long buyerId, int allPoint){
        return pointRepository.save(new Point(buyerId, allPoint));
    }
}

