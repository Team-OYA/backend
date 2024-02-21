package com.oya.kr.community.repository;

import static com.oya.kr.community.exception.CommunityErrorCodeList.NOT_EXIST_VOTE;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.community.controller.dto.response.VoteResponse;
import com.oya.kr.community.exception.CommunityErrorCodeList;
import com.oya.kr.community.mapper.VoteMapper;
import com.oya.kr.community.mapper.dto.request.VoteCheckMapperRequest;
import com.oya.kr.global.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VoteRepository {

    private final VoteMapper voteMapper;

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @return List<VoteResponse>
     * @author 김유빈
     * @since 2024.02.21
     */
    public List<VoteResponse> getVoteList(Long communityId, Long userId) {
        List<VoteResponse> voteResponseList = voteMapper.findByPostId(communityId);
        voteResponseList.forEach(VoteResponse -> {
            boolean check = Boolean.parseBoolean(voteMapper.findByVoteIdAndUserId(VoteResponse.getVote_id(), userId));
            VoteResponse.setChecked(check);
        });
        return voteResponseList;
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @author 김유빈
     * @since 2024.02.21
     */
    public void save(Long id, Long userId) {
        voteMapper.findById(id).orElseThrow(() -> new ApplicationException(NOT_EXIST_VOTE));
        VoteCheckMapperRequest request = new VoteCheckMapperRequest(userId, id);
        int count = voteMapper.findByUserIdAndVoteId(request);
        if (count >= 1) {
            throw new ApplicationException(CommunityErrorCodeList.VALID_VOTE);
        }
        voteMapper.save(request);
    }

    /**
     * repository 계층으로 분리
     *
     * @parameter Long, Long
     * @author 김유빈
     * @since 2024.02.21
     */
    public void deleteByVoteCheck(Long id, Long userId) {
        voteMapper.deleteByVoteCheck(new VoteCheckMapperRequest(userId, id));
    }
}
