<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- roomDetails.jsp 파일 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>

<h1>Chat Room Details</h1>

<p>Room ID: ${chatRoomDetailResponse.chatRoomId}</p>
<p>Room Name: ${chatRoomDetailResponse.name}</p>

<button onclick="connect()">Connect</button>
<button onclick="disconnect()">Disconnect</button>

<div id="messages">
<c:forEach var="chatRoomResponse" items="${chatMessage}">
        Sender Nickname: ${chatRoomResponse.senderNickname} Message: ${chatRoomResponse.message} Sending Time: ${chatRoomResponse.sendingTime}
        <br/>
</c:forEach>
</div>


<input type="text" id="message" placeholder="Enter your message"/>
<button onclick="sendMessage()">Send</button>

<script type="text/javascript">
    var stompClient = null;

    function connect() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // Subscribe to the topic for receiving chat messages
            stompClient.subscribe('/topic/messages', function (message) {
                showMessage(JSON.parse(message.body));
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    var previousMessagesDiv = document.createElement('div');

    function sendMessage() {
        var message = document.getElementById('message').value;
        var chatMessageRequest = {
            roomId: ${chatRoomDetailResponse.chatRoomId}, // Set the actual room ID here
            sender: 'username',   // Set the actual sender's username here
            message: message,
            sendingTime: new Date().toISOString()
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessageRequest));
    }

    function showMessage(message) {
        var messageDiv = document.createElement('div');
        messageDiv.innerHTML = "<p>" + message.sender + " (" + message.sendingTime + "): " + message.message + "</p>";

        // 현재 메시지 div를 이전 메시지 div에 추가
        // previousMessagesDiv.appendChild(messageDiv);

        document.getElementById('messages').appendChild(messageDiv);
    }
    connect(); // Connect to the WebSocket when the page loads
</script>

</body>
</html>
