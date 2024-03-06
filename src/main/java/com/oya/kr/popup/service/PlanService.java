package com.oya.kr.popup.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oya.kr.global.support.MailSender;
import com.oya.kr.global.support.StorageConnector;
import com.oya.kr.global.support.dto.request.SenderRequest;
import com.oya.kr.popup.controller.dto.request.PlanSaveRequest;
import com.oya.kr.popup.controller.dto.response.AllPlanResponse;
import com.oya.kr.popup.controller.dto.response.AllPlansResponse;
import com.oya.kr.popup.controller.dto.response.CategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentCategoryResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentFloorsWithCategoriesResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentResponse;
import com.oya.kr.popup.controller.dto.response.DepartmentsResponse;
import com.oya.kr.popup.controller.dto.response.EntranceStatusResponses;
import com.oya.kr.popup.controller.dto.response.MyPlanResponse;
import com.oya.kr.popup.controller.dto.response.MyPlansResponse;
import com.oya.kr.popup.controller.dto.response.PlanResponse;
import com.oya.kr.popup.controller.dto.response.PlansResponse;
import com.oya.kr.popup.controller.dto.response.PopupDetailResponse;
import com.oya.kr.popup.controller.dto.response.UserPlanResponse;
import com.oya.kr.popup.domain.Popup;
import com.oya.kr.popup.domain.enums.Category;
import com.oya.kr.popup.domain.enums.Department;
import com.oya.kr.popup.domain.Plan;
import com.oya.kr.popup.domain.enums.EntranceStatus;
import com.oya.kr.popup.mapper.dto.response.AllPlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.MyPlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.MyPopupDetailMapper;
import com.oya.kr.popup.mapper.dto.response.PlanAboutMeMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PlanMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupDetailMapperResponse;
import com.oya.kr.popup.mapper.dto.response.PopupMapperResponse;
import com.oya.kr.popup.repository.PlanRepository;
import com.oya.kr.popup.repository.PopupRepository;
import com.oya.kr.user.domain.User;
import com.oya.kr.user.repository.UserRepository;

/**
 * @author 김유빈
 * @since 2024.02.14
 */
@Service
@Transactional
public class PlanService {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PopupRepository popupRepository;
    private final StorageConnector s3Connector;
    private final MailSender sesSender;

    public PlanService(
        UserRepository userRepository,
        PlanRepository planRepository, PopupRepository popupRepository,
        StorageConnector s3Connector,
        @Qualifier("SESSender") MailSender sesSender) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
		this.popupRepository = popupRepository;
		this.s3Connector = s3Connector;
        this.sesSender = sesSender;
    }

    /**
     * 카테고리 리스트 조회 기능 구현
     *
     * @parameter String
     * @return CategoriesResponse
     * @author 김유빈
     * @since 2024.02.27
     */
    @Transactional(readOnly = true)
    public CategoriesResponse findAllCategories(String email) {
        Category[] categories = Category.values();
        return CategoriesResponse.from(categories);
    }

    /**
     * 사업계획서 진행 단계 리스트 조회 기능 구현
     *
     * @parameter String
     * @return EntranceStatusResponses
     * @author 김유빈
     * @since 2024.02.27
     */
    @Transactional(readOnly = true)
    public EntranceStatusResponses findAllEntranceStatus(String email) {
        EntranceStatus[] entranceStatuses = EntranceStatus.values();
        return EntranceStatusResponses.from(entranceStatuses);
    }

    /**
     * 현대백화점 지점 리스트 조회 기능 구현
     *
     * @return DepartmentsResponse
     * @author 김유빈
     * @since 2024.02.14
     */
    @Transactional(readOnly = true)
    public DepartmentsResponse findAllDepartment() {
        Department[] departments = Department.values();
        return new DepartmentsResponse(
            Arrays.stream(departments)
                .map(DepartmentResponse::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    /**
     * 팝업스토어 카테고리 별 층수 리스트 조회 기능 구현
     *
     * @return DepartmentFloorsWithCategoriesResponse
     * @author 김유빈
     * @since 2024.02.14
     */
    @Transactional(readOnly = true)
    public DepartmentFloorsWithCategoriesResponse findAllFloor() {
        Category[] categories = Category.values();
        return new DepartmentFloorsWithCategoriesResponse(
            Arrays.stream(categories)
                .map(DepartmentCategoryResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 팝업스토어 게시글이 없는 사업계획서 리스트 조회 기능 구현
     *
     * @parameter String
     * @return PlansResponse
     * @author 김유빈
     * @since 2024.02.19
     */
    @Transactional(readOnly = true)
    public PlansResponse findAllWithoutPopup(String email) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();
        return new PlansResponse(
            planRepository.findAllWithoutPopup(savedUser).stream()
                .map(PlanResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 나의 사업계획서 리스트 조회 기능 구현
     *
     * @parameter String, String, String, int, int
     * @return MyPlansResponse
     * @author 김유빈
     * @since 2024.02.27
     */
    @Transactional(readOnly = true)
    public MyPlansResponse findAllAboutMe(String email, String category, String entranceStatus, int pageNo, int amount) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        Category savedCategory = Category.from(category);
        EntranceStatus savedEntranceStatus = EntranceStatus.from(entranceStatus);

        List<PlanAboutMeMapperResponse> mapperResponses = planRepository.findAllAboutMe(savedUser.getId(),
            savedCategory, savedEntranceStatus, pageNo, amount);
        int total = planRepository.countAboutMe(savedUser.getId(), savedCategory, savedEntranceStatus);

        return new MyPlansResponse(
            total,
            mapperResponses.stream()
                .map(MyPlanResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 모든 사업계획서 리스트 조회 기능 구현
     *
     * @parameter String, String, String, int, int
     * @return AllPlansResponse
     * @author 김유빈
     * @since 2024.02.27
     */
    @Transactional(readOnly = true)
    public AllPlansResponse findAll(String email, String category, String entranceStatus, int pageNo, int amount) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        Category savedCategory = Category.from(category);
        EntranceStatus savedEntranceStatus = EntranceStatus.from(entranceStatus);

        List<AllPlanMapperResponse> mapperResponses = planRepository.findAll(savedCategory, savedEntranceStatus, pageNo, amount);
        int total = planRepository.countAboutAll(savedCategory, savedEntranceStatus);

        return new AllPlansResponse(
            total,
            mapperResponses.stream()
                .map(AllPlanResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    /**
     * 사업계획서 제안 기능 구현
     *
     * @parameter String, PlanSaveRequest, MultipartFile
     * @return long
     * @author 김유빈
     * @since 2024.02.16
     */
    public long save(String email, PlanSaveRequest request, MultipartFile businessPlan) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        String businessPlanUrl = s3Connector.save(businessPlan);

        Plan plan = Plan.saved(savedUser, request.getOffice(), request.getFloor(), request.getOpenDate(), request.getCloseDate(),
            businessPlanUrl, request.getContactInformation(), request.getCategory());
        return planRepository.save(plan);
    }

    /**
     * 사업계획서 철회 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void withdraw(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsBusiness();

        Plan savedPlan = planRepository.findById(planId, savedUser);
        savedPlan.withdraw(savedUser);
        planRepository.updateEntranceStatus(savedPlan);
    }

    /**
     * 사업계획서 대기 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void wait(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        PlanMapperResponse response = planRepository.findByIdWithoutUser(planId);
        User businessUser = userRepository.findByUserId(response.getUserId());

        Plan savedPlan = response.toDomain(businessUser);
        savedPlan.waiting();
        planRepository.updateEntranceStatus(savedPlan);

        String title = "[THEPOP] 제안해주신 사업계획서가 대기 상태로 전환되었습니다.";
        String content = String.format(
            "%s 고객님, 안녕하세요.\n"
                + "제안해주신 사업계획서가 대기 상태로 전환되었습니다.\n"
                + "대기 상태에는 커뮤니티 게시글을 자유롭게 작성하실 수 있습니다.\n"
                + "이후 제안이 승인되면 팝업스토어 게시글을 올려 팝업스토어를 홍보하실 수 있습니다.\n"
                + "팝업스토어 제안에 감사드립니다.\n"
                + "THEPOP 드림", savedUser.getNickname());

        SenderRequest request = new SenderRequest(
            savedUser.getEmail(), List.of(businessUser.getEmail()), title, content);
        sesSender.send(request);
    }

    /**
     * 사업계획서 승인 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void approve(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        PlanMapperResponse response = planRepository.findByIdWithoutUser(planId);
        User businessUser = userRepository.findByUserId(response.getUserId());

        Plan savedPlan = response.toDomain(businessUser);
        savedPlan.approve();
        planRepository.updateEntranceStatus(savedPlan);

        String title = "[THEPOP] 제안해주신 사업계획서가 승인되었습니다.";
        String content = String.format(
            "%s 고객님, 안녕하세요.\n"
                + "팝업스토어 입점을 축하드립니다!\n"
                + "사업계획서가 승인되어 팝업스토어 게시글을 올려 팝업스토어를 홍보하실 수 있습니다.\n"
                + "팝업스토어 제안에 감사드립니다.\n"
                + "THEPOP 드림", savedUser.getNickname());

        SenderRequest request = new SenderRequest(
            savedUser.getEmail(), List.of(savedPlan.getUser().getEmail()), title, content);
        sesSender.send(request);
    }

    /**
     * 사업계획서 거절 기능 구현
     *
     * @parameter String, Long
     * @author 김유빈
     * @since 2024.02.18
     */
    public void deny(String email, Long planId) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.validateUserIsAdministrator();

        PlanMapperResponse response = planRepository.findByIdWithoutUser(planId);
        User businessUser = userRepository.findByUserId(response.getUserId());

        Plan savedPlan = response.toDomain(businessUser);
        savedPlan.deny();
        planRepository.updateEntranceStatus(savedPlan);

        String title = "[THEPOP] 제안해주신 사업계획서가 거절되었습니다.";
        String content = String.format(
            "%s 고객님, 안녕하세요.\n"
                + "안타깝지만 제안해주신 사업계획서는 현대백화점과 함께하지 못하게 되었습니다.\n"
                + "이후에 더 좋은 사업계획서가 있다면 언제든 연락 바랍니다.\n"
                + "팝업스토어 제안에 감사드립니다.\n"
                + "THEPOP 드림", savedUser.getNickname());

        SenderRequest request = new SenderRequest(
            savedUser.getEmail(), List.of(savedPlan.getUser().getEmail()), title, content);
        sesSender.send(request);
    }

    /**
     * 나의 사업계획서 조회 구현
     *
     * @parameter String, Long
     * @author 이상민
     * @since 2024.02.29
     */
	public UserPlanResponse findById(Long planId) {
        MyPlanMapperResponse myPlanMapperResponse = planRepository.findByMyPlan(planId);
        return new UserPlanResponse(myPlanMapperResponse);
	}

    /**
     * 사업계획서에 따른 팝업스토어 상세 정보 보기
     *
     * @parameter Principal
     * @return PopupDetailResponse
     * @author 이상민
     * @since 2024.03.01
     */
    public PopupDetailResponse myPopup(String email, long planId) {
        Plan plan = planRepository.findById(planId);
        MyPopupDetailMapper myPopupDetailMapper = popupRepository.findByPlanId(plan.getId());

        boolean popupWritten = false;
        if(myPopupDetailMapper != null){
            popupWritten = true;
            List<String> images = popupRepository.findByImages(myPopupDetailMapper.getId()); // 팝업 이미지
            return new PopupDetailResponse(popupWritten, myPopupDetailMapper, images);
        }
        return new PopupDetailResponse(popupWritten);
    }
}
