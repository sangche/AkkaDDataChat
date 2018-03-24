angular.module("ChatApp", []).controller("ChatController", function($scope){
  // connect to websockets endpoint of our server
  var ws = new WebSocket('ws://' + window.location.host + '/socket');

  // binding model for the UI
  var chat = this;
  chat.messages = [];
  chat.currentMessage = "";
  chat.username = "";

  // user enters message
  chat.sendMessage = function() {
    var text = chat.username + ": " + chat.currentMessage;
    chat.currentMessage = "";
    ws.send(text);
  };

  // receive message from the webserver
  ws.onmessage = function(msg) {
    chat.messages.push(msg.data);
    $scope.$digest();
  };

});
