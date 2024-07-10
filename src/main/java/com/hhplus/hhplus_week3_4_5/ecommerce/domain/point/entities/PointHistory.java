package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entities;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entities.CreateDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.PointEnums;
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
public class PointHistory extends CreateDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("잔액 내역 id")
    private Long pointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    @Comment("잔액 id")
    private Point point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("잔액 타입 Enum")
    private PointEnums.Type type;

    @Comment("사용/차감한 포인트")
    private int usePoint;

    @Builder
    public PointHistory(Long pointHistoryId, Point point, PointEnums.Type type, int usePoint) {
        this.pointHistoryId = pointHistoryId;
        this.point = point;
        this.type = type;
        this.usePoint = usePoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointHistory that = (PointHistory) o;
        return Objects.equals(getPointHistoryId(), that.getPointHistoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPointHistoryId());
    }
}
