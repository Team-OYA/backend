package com.oya.kr.global.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DateConvertor {
	public static LocalDate dateFormat(String date) {
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			return LocalDate.parse(date, formatter);
		} else {
			return null;
		}
	}
}
