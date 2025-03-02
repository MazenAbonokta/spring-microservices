package com.bank.tech.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public  class BaseEntity{

    @CreatedDate
    @Column(updatable = false)
    private Instant createdDate;
    @Column(insertable = false)
    @LastModifiedDate
    private Instant updateDate ;
    @Column(updatable = false)
    private boolean deleted = Boolean.FALSE;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
}
