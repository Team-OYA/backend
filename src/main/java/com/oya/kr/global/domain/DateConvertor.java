package com.oya.kr.global.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DateConvertor {

	public static LocalDate convertDateFormatForRequest(String date) {
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			return LocalDate.parse(date, formatter);
		} else {
			return null;
		}
	}

	public static String convertDateFormatForResponse(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return date.format(formatter);
	}

	public static String convertDateFormatForResponse(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return date.format(formatter);
	}
}
