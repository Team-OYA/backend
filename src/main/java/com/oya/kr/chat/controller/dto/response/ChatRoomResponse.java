// package com.oya.kr.chat.controller.dto.response;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
//
// import com.oya.kr.chat.controller.dto.request.ChatMessageRequest;
//
// import lombok.Getter;
// import lombok.Setter;
//
// @Getter
// @Setter
// public class ChatRoomResponse {
// 	private String id;
// 	private String name;
// 	private List<ChatMessageRequest> messages;
//
// 	public ChatRoomResponse(String name) {
// 		this.id = UUID.randomUUID().toString();
// 		this.name = name;
// 		this.messages = new ArrayList<>();
// 	}
// }