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

<div id="messages"></div>

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
        document.getElementById('messages').appendChild(messageDiv);
    }

    connect(); // Connect to the WebSocket when the page loads
</script>

<style>
    body {
        font-family: 'Arial', sans-serif;
        margin: 20px;
    }
    h1 {
        color: #333;
    }
    p {
        color: #666;
    }
    button {
        padding: 10px;
        margin: 5px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 3px;
        cursor: pointer;
    }
    #messages {
        margin-top: 20px;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
        max-height: 300px;
        overflow-y: auto;
    }
    input[type="text"] {
        padding: 10px;
        margin: 5px;
        width: 70%;
    }
    button[type="button"] {
        width: 28%;
    }
    input[type="text"], button[type="button"] {
        display: inline-block;
    }
    input[type="text"]:focus {
        outline: none;
    }
    button[type="button"]:hover {
        background-color: #45a049;
    }
    button[type="button"]:focus {
        outline: none;
    }
</style>

</body>
</html>
