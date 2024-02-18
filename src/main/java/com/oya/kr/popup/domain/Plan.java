package com.oya.kr.popup.domain;

import static com.oya.kr.global.exception.GlobalErrorCodeList.CLOSE_DATE_IS_NOT_AFTER_OPEN_DATE;
import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_ENTRANCE_STATUS_IS_REQUEST;
import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_ENTRANCE_STATUS_IS_REQUEST_OR_WAITING;
import static com.oya.kr.popup.exception.PlanErrorCodeList.NOT_ENTRANCE_STATUS_IS_WAITING;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.global.domain.RegexValidator;
import com.oya.kr.global.exception.ApplicationException;
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

	private Long id;
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

	private Plan(User user, DepartmentBranch departmentBranch, DepartmentFloor floor, String location,
		LocalDate openDate, LocalDate closeDate, String businessPlanUrl, EntranceStatus entranceStatus,
		String contactInformation, Category category) {
		this.user = user;
		this.departmentBranch = departmentBranch;
		this.floor = floor;
		this.location = location;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.businessPlanUrl = businessPlanUrl;
		this.entranceStatus = entranceStatus;
		this.contactInformation = contactInformation;
		this.category = category;
	}

	/**
	 * 사업계획서 저장 정적 팩터리 메서드 구현
	 *
	 * @parameter User, String, String, LocalDate, LocalDate, String, String, String
	 * @return Plan
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public static Plan saved(User user, String office, String floor, LocalDate openDate, LocalDate closeDate,
		String businessPlanUrl, String contactInformation, String category) {
		validateCloseDateIsAfterOpenDate(openDate, closeDate);
		RegexValidator.validateContactInformationIsAppropriateForFormatting(contactInformation);
		DepartmentFloor selectedFloor = DepartmentFloor.from(floor);
		return new Plan(user, DepartmentBranch.from(office), selectedFloor, null, openDate, closeDate,
			businessPlanUrl, EntranceStatus.REQUEST, contactInformation, Category.from(category));
	}

	/**
	 * 사업계획서 철회 기능 구현
	 *
	 * @parameter User
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public void withdraw(User user) {
		this.user.validateUserIsOwner(user);
		validateEntranceStatusIsRequest();
		this.entranceStatus = EntranceStatus.WITHDRAWAL;
	}

	/**
	 * 사업계획서 대기 기능 구현
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public void waiting() {
		validateEntranceStatusIsRequest();
		this.entranceStatus = EntranceStatus.WAITING;
	}

	/**
	 * 사업계획서 승인 기능 구현
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public void approve() {
		validateEntranceStatusIsWaiting();
		this.entranceStatus = EntranceStatus.APPROVAL;
	}

	/**
	 * 사업계획서 거절 기능 구현
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	public void deny() {
		validateItBeInEntranceStatusOfDeny();
		this.entranceStatus = EntranceStatus.REJECTION;
	}

	/**
	 * 종료 날짜가 시작 날짜 이후인지 검증
	 *
	 * @parameter LocalDate, LocalDate
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	private static void validateCloseDateIsAfterOpenDate(LocalDate openDate, LocalDate closeDate) {
		if (!closeDate.isAfter(openDate)) {
			throw new ApplicationException(CLOSE_DATE_IS_NOT_AFTER_OPEN_DATE);
		}
	}

	/**
	 * 입점 상태가 요청 상태인지 검증
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	private void validateEntranceStatusIsRequest() {
		if (!this.entranceStatus.isRequest()) {
			throw new ApplicationException(NOT_ENTRANCE_STATUS_IS_REQUEST);
		}
	}

	/**
	 * 입점 상태가 대기 상태인지 검증
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	private void validateEntranceStatusIsWaiting() {
		if (!this.entranceStatus.isWaiting()) {
			throw new ApplicationException(NOT_ENTRANCE_STATUS_IS_WAITING);
		}
	}

	/**
	 * 입점 상태가 거절 가능한 상태인지 검증
	 *
	 * @author 김유빈
	 * @since 2024.02.18
	 */
	private void validateItBeInEntranceStatusOfDeny() {
		if (!(this.entranceStatus.isRequest() || this.entranceStatus.isWaiting())) {
			throw new ApplicationException(NOT_ENTRANCE_STATUS_IS_REQUEST_OR_WAITING);
		}
	}
}
