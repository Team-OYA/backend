package com.oya.kr.user.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.user.mapper.dto.request.SignupKakaoRequest;
import com.oya.kr.user.mapper.dto.request.SignupUserMapperRequest;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

public interface UserMapper {
	Optional<UserMapperResponse> findByEmail(@Param("email") String email);

	int insertUser(SignupUserMapperRequest signupUserMapperRequest);

	Integer duplicatedEmail(String email);

	Integer duplicatedNickname(String nickname);

	int insertKakaoUser(SignupKakaoRequest signupKakaoRequest);
}
