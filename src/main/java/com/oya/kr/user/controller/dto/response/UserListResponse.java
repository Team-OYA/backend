package com.oya.kr.user.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserListResponse {

	private final int sum;
	private final List<? extends BasicUserResponse> userList;
}
