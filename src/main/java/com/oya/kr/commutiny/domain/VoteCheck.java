package com.oya.kr.commutiny.domain;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;
import com.oya.kr.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteCheck extends Base {

	private Vote vote;
	private User user;
}
