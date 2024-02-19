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
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.PopupImage;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupImageSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.BusinessMapper;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public class PopupImageMapperTest extends SpringApplicationTest {

    @Autowired
    private PopupImageMapper popupImageMapper;

    @Autowired
    private PopupMapper popupMapper;

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
     * @since 2024.02.19
     */
    @BeforeEach
    void setUp() {
        popupImageMapper.deleteAll();
        popupMapper.deleteAll();
        businessMapper.deleteAll();
        planMapper.deleteAll();
        userMapper.deleteAll();
    }

    /**
     * 테스트 후 데이터 초기화
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @AfterEach
    void init() {
        popupImageMapper.deleteAll();
        popupMapper.deleteAll();
        businessMapper.deleteAll();
        planMapper.deleteAll();
        userMapper.deleteAll();
    }

    /**
     * save 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("팝업스토어 게시글의 메인 이미지를 저장한다")
    @Test
    void save() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup savedPopup = savedPopup(savedPlan);

        PopupImage popupImage = PopupImage.saved("url", savedPopup);
        PopupImageSaveMapperRequest request = PopupImageSaveMapperRequest.from(popupImage);

        // when & then
        assertThatCode(() -> popupImageMapper.save(request))
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

    private Plan savedPlan(User savedUser) {
        Plan plan = Plan.saved(
            savedUser,
            DepartmentBranch.THE_HYUNDAI_SEOUL.getCode(),
            DepartmentFloor.B1.getCode(),
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            "businessPlanUrl",
            "010-1234-5678",
            Category.CLOTHING.getCode());
        PlanSaveMapperRequest request = PlanSaveMapperRequest.from(plan);
        planMapper.save(request);
        return planMapper.findById(request.getPlanId()).get().toDomain(savedUser);
    }

    private Popup savedPopup(Plan savedPlan) {
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);
        popupMapper.save(request);
        return popupMapper.findById(request.getPopupId()).get().toDomain(savedPlan);
    }
}
