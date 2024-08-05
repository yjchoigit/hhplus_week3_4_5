package com.hhplus.ecommerce.domain.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CreateModifyDateTimeEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDatetime;

    @LastModifiedDate
    private LocalDateTime modifyDatetime;

    @PrePersist
    public void prePersist() {
        this.modifyDatetime = null;
    }

    public void udpateData() {
        this.modifyDatetime = LocalDateTime.now();
    }
}
