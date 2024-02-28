<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat Rooms</title>
</head>
<body>

<h1>Chat Rooms</h1>

<ul>
    <c:forEach var="chatRoomResponse" items="${chatRoomResponses}">
        <li><a href="/chat/room/${chatRoomResponse.chatRoomId}">${chatRoomResponse.name}</a></li>
    </c:forEach>
</ul>

<form action="/chat/createRoom" method="post">
    <label for="roomName">Room Name:</label>
    <input type="text" id="roomName" name="roomName" required>
    <button type="submit">Create Room</button>
</form>

</body>
</html>
