package com.oya.kr.popup.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Plan extends Base {

	private User user;
	private DepartmentBranch departmentBranch;
	private DepartmentFloor floor;
	private String location;
	private LocalDate openDate;
	private LocalDate closeDate;
	private String businessPlanUrl;
	private EntranceStatus entranceStatus;
	private String contactInformation;
	private Category category;

	public static Plan saved(User user, String office, String floor, LocalDate openDate, LocalDate closeDate,
		String businessPlanUrl, String contactInformation, String category) {
		DepartmentFloor selectedFloor = DepartmentFloor.from(floor);
		return new Plan(user, DepartmentBranch.from(office), selectedFloor, null, openDate, closeDate,
			businessPlanUrl, EntranceStatus.REQUEST, contactInformation, Category.from(category));
	}
}
