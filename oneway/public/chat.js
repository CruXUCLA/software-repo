// Make connection
var socket = io.connect('http://172.28.4.244:3000');
//http://192.168.1.249:3000
var message = document.getElementById('message'),
      btn = document.getElementById('send');

// Emit events
btn.addEventListener('click', function(){
    socket.emit('chat',  message.value);
    message.value = "";
    });
