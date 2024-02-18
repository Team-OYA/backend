package com.oya.kr.community.domain;

import org.springframework.stereotype.Component;

import com.oya.kr.global.domain.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityAdvertisement extends Base {

	private Long id;
	private Community community;
	private int amount;
}
