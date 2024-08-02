package com.hhplus.ecommerce.domain.point.entity;

import com.hhplus.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.ecommerce.domain.point.PointEnums;
import com.hhplus.ecommerce.domain.point.exception.PointCustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("잔액 id")
    private Long pointId;

    @Column(nullable = false)
    @Comment("회원 id")
    private Long buyerId;

    @Comment("총 잔액")
    private int allPoint;

    public Point(Long pointId, Long buyerId, int allPoint) {
        this.pointId = pointId;
        this.buyerId = buyerId;
        this.allPoint = allPoint;
    }

    public Point(Long buyerId, int allPoint) {
        this.buyerId = buyerId;
        this.allPoint = allPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(getPointId(), point.getPointId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPointId());
    }

    // 잔액 충전
    public void charge(int point) {
        this.allPoint += point;
    }

    // 잔액 사용
    public void use(int point) {
        if(allPoint == 0 || allPoint < point) {
            throw new PointCustomException(PointEnums.Error.OUT_OF_POINT);
        }
        this.allPoint -= point;
    }
}
