package com.oya.kr.user.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.oya.kr.user.mapper.dto.request.FindByUserMapperRequest;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;
import com.oya.kr.user.mapper.dto.response.AdUserDetailMapperResponse;
import com.oya.kr.user.mapper.dto.response.BasicMapperResponse;
import com.oya.kr.user.mapper.dto.response.BusinessMapperResponse;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

/**
 * @author 이상민
 * @since 2024.02.14
 */
public interface UserMapper {

	Optional<UserMapperResponse> findByEmail(@Param("email") String email);

	int insertUser(SignupAdministratorMapperRequest signupAdministratorMapperRequest);

	Integer duplicatedEmail(String email);

	Integer duplicatedNickname(String nickname);

	int insertAdminAndKakaoUser(SignupBasicMapperRequest signupBasicMapperRequest);

	void deleteAll();

	Optional<UserMapperResponse> findById(long userId);

	void deleteFromUserId(Long id);

	List<BasicMapperResponse> findByBasic(FindByUserMapperRequest findByUserMapperRequest);

	List<BusinessMapperResponse> findByBusiness(FindByUserMapperRequest findByUserMapperRequest);

	Optional<AdUserDetailMapperResponse> findMeForAd(Long userId);

	int countUser(String type);
}
