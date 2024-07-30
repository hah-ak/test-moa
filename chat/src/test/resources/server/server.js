const StompJs = require('@stomp/stompjs');
const WebSocket = require('ws');

// WebSocket을 지원하도록 설정
global.WebSocket = WebSocket;

const client = new StompJs.Client({
    brokerURL: 'ws://localhost:8090/chat/connection',
    connectHeaders: {
        login: 'user',
        passcode: 'password',
    },
    debug: function (str) {
        console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

client.onConnect = function (frame) {
    console.log('Connected: ' + frame);

    // 토픽 구독
    client.subscribe('/chat/sub/messages', function (message) {
        console.log('Received: ' + message.body);
    });

    // 메시지 전송
    client.publish({
        destination: '/chat/pub/room.enter.1',
        body: JSON.stringify({ sender: 'Terminal', content: 'Hello from terminal!' }),
    });
};

client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
};

client.activate();