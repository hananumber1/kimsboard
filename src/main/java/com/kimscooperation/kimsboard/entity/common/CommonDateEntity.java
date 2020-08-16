package com.kimscooperation.kimsboard.entity.common;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity {

	@CreatedDate
	private LocalDateTime createdAt; // Entity 생성시 자동으로 날짜세팅

	@LastModifiedDate
	private LocalDateTime modifiedAt; // Entity 수정시 자동으로 날짜세팅
}