package com.oya.kr.popup.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oya.kr.common.SpringApplicationTest;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.DepartmentBranch;
import com.oya.kr.popup.domain.enums.DepartmentFloor;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.enums.WithdrawalStatus;
import com.oya.kr.popup.mapper.dto.request.PlanSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupCollectionSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSaveMapperRequest;
import com.oya.kr.popup.mapper.dto.request.PopupSearchMapperRequest;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;
import com.oya.kr.user.controller.dto.request.JoinRequest;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.mapper.BusinessMapper;
import com.oya.kr.user.mapper.UserMapper;
import com.oya.kr.user.mapper.dto.request.SignupAdministratorMapperRequest;

/**
 * @author 김유빈
 * @since 2024.02.19
 */
public class PopupMapperTest extends SpringApplicationTest {

    @Autowired
    private PopupMapper popupMapper;

    @Autowired
    private PopupCollectionMapper popupCollectionMapper;

    @Autowired
    private PopupViewMapper popupViewMapper;

    @Autowired
    private PopupImageMapper popupImageMapper;

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
        popupCollectionMapper.deleteAll();
        popupViewMapper.deleteAll();
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
        popupCollectionMapper.deleteAll();
        popupViewMapper.deleteAll();
        popupImageMapper.deleteAll();
        popupMapper.deleteAll();
        businessMapper.deleteAll();
        planMapper.deleteAll();
        userMapper.deleteAll();
    }

    /**
     * findById 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("아이디를 이용하여 팝업스토어 게시글을 조회한다")
    @Test
    void findById() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);
        popupMapper.save(request);

        // when
        Optional<PopupMapperResponse> mapperResponse = popupMapper.findById(request.getPopupId());

        // then
        assertThat(mapperResponse).isPresent();
    }

    /**
     * findByIdWithDate 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("아이디를 이용하여 오픈 일정이 포함된 팝업스토어 게시글을 조회한다")
    @Test
    void findByIdWithDate() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);
        popupMapper.save(request);

        // when
        Optional<PopupDetailMapperResponse> mapperResponse = popupMapper.findByIdWithDate(request.getPopupId());

        // then
        assertThat(mapperResponse).isPresent();
    }

    /**
     * findAllByPlanId 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("사업계획서 아이디를 이용하여 팝업스토어 게시글 목록을 조회한다")
    @Test
    void findAllByPlanId() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);
        popupMapper.save(request);

        // when
        List<PopupMapperResponse> mapperResponses = popupMapper.findAllByPlanId(request.getPlanId());

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * findAll 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("모든 팝업스토어 게시글 목록을 조회한다")
    @Test
    void findAll() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), 0, 5);

        // when
        List<PopupDetailMapperResponse> mapperResponses = popupMapper.findAll(request);

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * findInProgress 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("진행중인 팝업스토어 게시글 목록을 조회한다")
    @Test
    void findInProgress() {
        // given
        LocalDate openDate = LocalDate.now();
        LocalDate closeDate = openDate.plusDays(1);

        User savedUser = savedUser();
        Plan savedPlan = savedPlanWithOpenAndCloseDate(savedUser, openDate, closeDate);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), 0, 5);

        // when
        List<PopupDetailMapperResponse> mapperResponses = popupMapper.findInProgress(request);

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * findScheduled 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("예정인 팝업스토어 게시글 목록을 조회한다")
    @Test
    void findScheduled() {
        // given
        LocalDate openDate = LocalDate.now().plusDays(1);
        LocalDate closeDate = openDate.plusDays(1);

        User savedUser = savedUser();
        Plan savedPlan = savedPlanWithOpenAndCloseDate(savedUser, openDate, closeDate);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), 0, 5);

        // when
        List<PopupDetailMapperResponse> mapperResponses = popupMapper.findScheduled(request);

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * findCollections 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.21
     */
    @DisplayName("스크랩한 팝업스토어 게시글 목록을 조회한다")
    @Test
    void findCollections() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        popupCollectionMapper.save(new PopupCollectionSaveMapperRequest(savedUser.getId(), popupSaveMapperRequest.getPopupId()));

        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), 0, 5);

        // when
        List<PopupDetailMapperResponse> mapperResponses = popupMapper.findCollections(request);

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * findAllRecommended 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.20
     */
    @DisplayName("팝업스토어 게시글 추천 목록을 조회한다")
    @Test
    void findAllRecommended() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest popupSaveMapperRequest = PopupSaveMapperRequest.from(popup);
        popupMapper.save(popupSaveMapperRequest);

        PopupSearchMapperRequest request = new PopupSearchMapperRequest(WithdrawalStatus.APPROVAL.getName(), 0, 5);

        // when
        List<PopupDetailMapperResponse> mapperResponses = popupMapper.findAllRecommended(request);

        // then
        assertThat(mapperResponses).hasSize(1);
    }

    /**
     * save 메서드 테스트 작성
     *
     * @author 김유빈
     * @since 2024.02.19
     */
    @DisplayName("팝업스토어 게시글을 작성한다")
    @Test
    void save() {
        // given
        User savedUser = savedUser();
        Plan savedPlan = savedPlan(savedUser);
        Popup popup = Popup.saved(savedPlan, "title", "description");
        PopupSaveMapperRequest request = PopupSaveMapperRequest.from(popup);

        // when & then
        assertThatCode(() -> popupMapper.save(request))
            .doesNotThrowAnyException();
    }

    // todo: fixture 분리
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

    private Plan savedPlanWithOpenAndCloseDate(User savedUser, LocalDate openDate, LocalDate closeDate) {
        Plan plan = Plan.saved(
            savedUser,
            DepartmentBranch.THE_HYUNDAI_SEOUL.getCode(),
            DepartmentFloor.B1.getCode(),
            openDate,
            closeDate,
            "businessPlanUrl",
            "010-1234-5678",
            Category.CLOTHING.getCode());
        PlanSaveMapperRequest request = PlanSaveMapperRequest.from(plan);
        planMapper.save(request);
        return planMapper.findById(request.getPlanId()).get().toDomain(savedUser);
    }
}
