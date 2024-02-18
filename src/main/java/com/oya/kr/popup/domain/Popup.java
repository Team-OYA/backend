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

	private Popup(Plan plan, String title, String description, LocalDateTime pulledDate, WithdrawalStatus withdrawalStatus) {
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
