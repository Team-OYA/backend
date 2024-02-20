package com.oya.kr.global.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Base {

	protected LocalDateTime createdDate;
	protected LocalDateTime modifiedDate;
	protected boolean deleted;
}
