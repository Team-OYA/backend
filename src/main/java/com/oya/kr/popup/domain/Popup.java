package com.oya.kr.popup.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.popup.domain.enums.WithdrawalStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Popup extends Base {

	private Long id;
	private Plan plan;
	private String content;
	private String title;
	private String description;
	private LocalDateTime pulledDate;
	private WithdrawalStatus withdrawalStatus;

	public Popup(Plan plan, String title, String content, String description, LocalDateTime pulledDate, WithdrawalStatus withdrawalStatus) {
		this.plan = plan;
		this.title = title;
		this.content = content;
		this.description = description;
		this.pulledDate = pulledDate;
		this.withdrawalStatus = withdrawalStatus;
	}

	public Popup(Long id, Plan plan, String title, String description, LocalDateTime pulledDate,
		WithdrawalStatus withdrawalStatus, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean deleted) {
		super(createdDate, modifiedDate, deleted);
		this.id = id;
		this.plan = plan;
		this.title = title;
		this.description = description;
		this.pulledDate = pulledDate;
		this.withdrawalStatus = withdrawalStatus;
	}

	public static Popup saved(Plan plan, String title, String content, String description) {
		return new Popup(plan, title, content, description, LocalDateTime.now(), WithdrawalStatus.DO_NOT_APPLY);
	}
}
