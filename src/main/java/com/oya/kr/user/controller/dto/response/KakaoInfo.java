package com.oya.kr.user.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oya.kr.user.domain.enums.UserType;

import lombok.Getter;
import lombok.Setter;

/**
 * Access Token 으로 요청한 프로필 응답 값 변환
 *
 * @author 이상민
 * @since 2024.01.26
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfo {

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	public String getEmail() {
		return kakaoAccount != null ? kakaoAccount.email : null;
	}

	public String getNickname() {
		return kakaoAccount != null && kakaoAccount.profile != null ? kakaoAccount.profile.nickname : null;
	}

	public String getProfileImage() {
		return kakaoAccount != null && kakaoAccount.profile != null ? kakaoAccount.profile.profileImageUrl : null;
	}

	public UserType getOAuthProvider() {
		return UserType.USER;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoAccount {
		private KakaoProfile profile;
		private String email;

		public KakaoAccount(KakaoProfile profile, String email) {
			this.profile = profile;
			this.email = email;
		}
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoProfile {
		private String nickname;
		@JsonProperty("profile_image_url")
		private String profileImageUrl; // 추가된 부분

		public KakaoProfile(String nickname, String url) {
			this.nickname = nickname;
			this.profileImageUrl = url;
		}
	}
}
