package com.likelion.sns.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass // 엔티티 클래스 끼리만 상속이 가능하기 때문, 각종 엔티티 클래스에 상속해주기 위해서 작성
@EntityListeners(value = AuditingEntityListener.class) // 엔티티의 변경사항, 이벤트에 대한 처리를 위해 작성
@Getter
// 해당 클래스를 단독으로 사용할 일이 없기때문에 추상클래스로 선언한다.
public abstract class BaseEntity {

    // updatable : 엔티티가 update될 경우 created_at이 update 되지 않게하기 위해 작성 (컬럼값 일관성 유지)
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private String updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

}