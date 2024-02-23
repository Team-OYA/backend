package com.oya.kr.user.repository;

import static com.oya.kr.user.exception.UserErrorCodeList.NOT_EXIST_USER;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.oya.kr.global.exception.ApplicationException;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.controller.dto.response.KakaoInfo;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;
import com.oya.kr.user.mapper.dto.request.SignupBasicMapperRequest;
import com.oya.kr.user.mapper.dto.response.BasicMapperResponse;
import com.oya.kr.user.mapper.dto.response.BusinessMapperResponse;
import com.oya.kr.user.mapper.dto.response.UserMapperResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author 김유빈
 * @since 2024.02.20
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @return User
     * @author 김유빈
     * @since 2024.02.20
     */
    public User findByEmail(String email) {
        return userMapper.findByEmail(email)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @return User
     * @author 김유빈
     * @since 2024.02.20
     */
    public User findByUserId(long userId) {
        return userMapper.findById(userId)
            .orElseThrow(() -> new ApplicationException(NOT_EXIST_USER))
            .toDomain();
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @return User
     * @author 김유빈
     * @since 2024.02.20
     */
    public User findByEmailForKakao(String email, Supplier<User> createUser) {
        return userMapper.findByEmail(email)
            .map(UserMapperResponse::toDomain)
            .orElseGet(createUser);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter JoinRequest
     * @return int
     * @author 김유빈
     * @since 2024.02.20
     */
    public int insertUser(JoinRequest joinRequest) {
        SignupAdministratorMapperRequest request = new SignupAdministratorMapperRequest(bCryptPasswordEncoder, joinRequest);
        return userMapper.insertUser(request);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter JoinRequest
     * @return int
     * @author 김유빈
     * @since 2024.02.20
     */
    public int insertAdminAndKakaoUser(JoinRequest joinRequest) {
        SignupBasicMapperRequest signupBasicMapperRequest = new SignupBasicMapperRequest(bCryptPasswordEncoder, joinRequest);
        return userMapper.insertAdminAndKakaoUser(signupBasicMapperRequest);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter KakaoInfo
     * @return int
     * @author 김유빈
     * @since 2024.02.20
     */
    public int insertAdminAndKakaoUser(KakaoInfo kakaoInfo) {
        SignupBasicMapperRequest signupBasicMapperRequest = new SignupBasicMapperRequest(kakaoInfo);
        return userMapper.insertAdminAndKakaoUser(signupBasicMapperRequest);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @return int
     * @author 김유빈
     * @since 2024.02.20
     */
    public Integer duplicatedEmail(String email) {
        return userMapper.duplicatedEmail(email);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter String
     * @return int
     * @author 김유빈
     * @since 2024.02.20
     */
    public Integer duplicatedNickname(String nickname) {
        return userMapper.duplicatedNickname(nickname);
    }


    public List<BusinessMapperResponse> findByBusinessAll(Long userId) {
        return userMapper.findByBusiness(userId);
    }

    public List<BasicMapperResponse> findByBasicAll(Long userId) {
        return userMapper.findByBasic(userId);
    }

    public List<BasicMapperResponse> getUserCommunityCount(Long userId, String type) {
        List<HashMap<String, Object>> map = userMapper.getUserCommunityCount(userId, type);
        return null;
    }
}
