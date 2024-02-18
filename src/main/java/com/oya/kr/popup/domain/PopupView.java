package com.oya.kr.popup.domain;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PopupView extends Base {

	private Long id;
	private Popup popup;
	private User user;
}
