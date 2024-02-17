package com.oya.kr.popup.mapper;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.popup.domain.Category;
import com.oya.kr.popup.domain.DepartmentBranch;
import com.oya.kr.popup.domain.DepartmentFloor;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PlanUpdateEntranceStatusMapperRequest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.BusinessMapper;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public class PlanMapperTest extends SpringApplicationTest {

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 테스트 전 데이터 초기화
     *
     * @author 김유빈
     * @since 2024.02.16
     */
    @BeforeEach
    void setUp() {
        businessMapper.deleteAll();
        planMapper.deleteAll();
        userMapper.deleteAll();
    }

    /**
     * 테스트 후 데이터 초기화
     *
     * @author 김유빈
     * @since 2024.02.16
     */
    @AfterEach
    void init() {
        businessMapper.deleteAll();
        planMapper.deleteAll();
        userMapper.deleteAll();
    }

    /**
     * save 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.16
     */
    @DisplayName("사업계획서를 저장한다")
    @Test
    void save() {
        // given
        User savedUser = savedUser();
        Plan plan = createDomain(savedUser);
        PlanSaveMapperRequest request = PlanSaveMapperRequest.from(plan);

        // when & then
        assertThatCode(() -> planMapper.save(request))
            .doesNotThrowAnyException();
    }

    /**
     * updateEntranceStatus 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.18
     */
    @DisplayName("사업계획서의 입점 상태를 변경한다")
    @Test
    void updateEntranceStatus() {
        // given
        User savedUser = savedUser();
        Plan plan = createDomain(savedUser);
        PlanSaveMapperRequest planSaveMapperRequest = PlanSaveMapperRequest.from(plan);
        planMapper.save(planSaveMapperRequest);
        Plan savedPlan = planMapper.findById(planSaveMapperRequest.getPlanId()).get().toDomain(savedUser);

        PlanUpdateEntranceStatusMapperRequest request = PlanUpdateEntranceStatusMapperRequest.from(savedPlan);

        // when & then
        assertThatCode(() -> planMapper.updateEntranceStatus(request))
            .doesNotThrowAnyException();
    }

    private User savedUser() {
        String email = "hansalchai0131@gmail.com";
        JoinRequest request = JoinRequest.builder()
            .email(email)
            .nickname("JohnDoe")
            .password("password123")
            .birthDate("19900101")
            .gender(1)
            .userType(2)
            .businessRegistrationNumber("1234567890")
            .profileUrl("/profile/johndoe.jpg")
            .nameOfCompany("ABC Inc.")
            .nameOfRepresentative("John Doe")
            .dateOfBusinessCommencement("20220101")
            .businessItem("IT Services")
            .connectedNumber("01012345678")
            .faxNumber("0212345678")
            .zipCode("123456")
            .businessAddress("123 Main St, City, Country")
            .build();
        userMapper.insertUser(new SignupAdministratorMapperRequest(bCryptPasswordEncoder, request));
        return userMapper.findByEmail(email).get().toDomain();
    }

    private static Plan createDomain(User savedUser) {
        return Plan.saved(
            savedUser,
            DepartmentBranch.THE_HYUNDAI_SEOUL.getCode(),
            DepartmentFloor.B1.getCode(),
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            "businessPlanUrl",
            "010-1234-5678",
            Category.CLOTHING.getCode());
    }
}
