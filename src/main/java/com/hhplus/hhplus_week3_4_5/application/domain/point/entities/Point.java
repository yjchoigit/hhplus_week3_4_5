package com.hhplus.hhplus_week3_4_5.application.domain.point.entities;

import com.hhplus.hhplus_week3_4_5.application.domain.base.entities.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.application.domain.buyer.entities.Buyer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    @Comment("회원 id")
    private Buyer buyer;

    @Comment("총 잔액")
    private int allPoint;

    @Builder
    public Point(Long pointId, Buyer buyer, int allPoint) {
        this.pointId = pointId;
        this.buyer = buyer;
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
}
