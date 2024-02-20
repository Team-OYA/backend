package com.oya.kr.popup.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Popup extends Base {

	private Long id;
	private Plan plan;
	private String title;
	private String description;
	private LocalDateTime pulledDate;
	private WithdrawalStatus withdrawalStatus;

	public Popup(Plan plan, String title, String description, LocalDateTime pulledDate, WithdrawalStatus withdrawalStatus) {
		this.plan = plan;
		this.title = title;
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

	public static Popup saved(Plan plan, String title, String description) {
		return new Popup(plan, title, description, LocalDateTime.now(), WithdrawalStatus.DO_NOT_APPLY);
	}
}
