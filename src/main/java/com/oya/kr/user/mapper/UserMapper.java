package com.oya.kr.user.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

public interface UserMapper {
	Optional<UserMapperResponse> findByEmail(@Param("email") String email);

	int insertUser(SignupAdministratorMapperRequest signupAdministratorMapperRequest);

	Integer duplicatedEmail(String email);

	Integer duplicatedNickname(String nickname);

	int insertAdminAndKakaoUser(SignupBasicMapperRequest signupBasicMapperRequest);
}
