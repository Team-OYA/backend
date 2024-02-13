package com.oya.kr.user.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.user.domain.User;
import com.oya.kr.user.service.dto.JoinRequestDto;

public interface UserMapper {
	Optional<User> findByEmail(@Param("email")String email);

	int insertUser(@Param("joinRequest") JoinRequestDto joinRequest);

	Integer duplicatedEmail(String email);

	Integer duplicatedNickname(String nickname);
}
