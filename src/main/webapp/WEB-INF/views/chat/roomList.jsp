<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Rooms</title>
</head>
<body>
<div class="container">
    <h1>Chat Rooms</h1>
    <table>
        <thead>
        <tr>
            <th>방번호</th>
            <th>방이름</th>
            <th>생성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="chatRoomResponse" items="${chatRoomResponses.list}">
            <tr>
                <td>${chatRoomResponse.chatRoomId}</td>
                <td><a href="/chat/room/${chatRoomResponse.chatRoomId}">${chatRoomResponse.name}</a></td>
                <td>${chatRoomResponse.createdDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="/chat/createRoom" method="post">
        <label for="roomName">Room Name:</label>
        <input type="text" id="roomName" name="roomName" required>
        <button type="submit">Create Room</button>
    </form>
</div>
</body>
</html>
