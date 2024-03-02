package com.oya.kr.chat.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oya.kr.chat.controller.dto.response.ChatRoomDetailResponse;
import com.oya.kr.chat.mapper.ChatRoomMapper;
import com.oya.kr.chat.mapper.dto.request.ChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.request.CreateChatRoomMapperRequest;
import com.oya.kr.chat.mapper.dto.response.ChatRoomDetailMapperResponse;
import com.oya.kr.global.dto.request.PaginationRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository { // 채팅 방 생성 및 정보 조회

	public final ChatRoomMapper chatRoomMapper;

	/**
	 * 채팅방 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public List<ChatRoomDetailResponse> findRoomByUser(ChatRoomMapperRequest chatRoomMapperRequest){
		List<ChatRoomDetailResponse> detailResponses = new ArrayList<>();
		List<ChatRoomDetailMapperResponse> list = chatRoomMapper.findRoomByUser(chatRoomMapperRequest);
		list.forEach(response->{
				detailResponses.add(new ChatRoomDetailResponse(response));
			});
		return detailResponses;
	}

	/**
	 * 관리자 채팅방 리스트 보여주기
	 *
	 * @author 이상민
	 * @since 2024.02.27
	 */
	public List<ChatRoomDetailResponse> findAllRoom(PaginationRequest paginationRequest) {
		List<ChatRoomDetailResponse> detailResponses = new ArrayList<>();
		List<ChatRoomDetailMapperResponse> list = chatRoomMapper.findByAll(paginationRequest);
		list.forEach(response->{
			detailResponses.add(new ChatRoomDetailResponse(response));
		});
		return detailResponses;
	}

	public ChatRoomDetailResponse findRoomById(Long roomId){
		return new ChatRoomDetailResponse(chatRoomMapper.findRoomById(roomId));
	}

	public void createChatRoom(CreateChatRoomMapperRequest createChatRoomMapperRequest){
		chatRoomMapper.save(createChatRoomMapperRequest);
	}

	public int count(Long userId) {
		return chatRoomMapper.count(userId);
	}

	public int countAll() {
		return chatRoomMapper.countAll();
	}
}
