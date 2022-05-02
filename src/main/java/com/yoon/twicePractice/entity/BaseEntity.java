package com.yoon.twicePractice.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 테이블로 생성되지 않음
@EntityListeners(value={AuditingEntityListener.class}) //JPA 내부에서 엔티티 객체 생성/변경 감지하는 리스너
@Getter
abstract class BaseEntity {

    @CreatedDate // JPA에서 엔티티 생성 시간 처리
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate //최종 수정 시간 자동처리
    @Column(name="moddate")
    private LocalDateTime modDate;
}
