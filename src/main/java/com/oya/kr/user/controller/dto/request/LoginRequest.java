package com.oya.kr.user.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class LoginRequest {
	private final String email;
	private final String password;

	@JsonCreator
	public LoginRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
		this.email = email;
		this.password = password;
	}
}
