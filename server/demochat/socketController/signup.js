let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');

let getSignup = (io, socket) => {
  return (username) => {
    if(arrUsername.indexOf(username) == -1){
      arrUsername.push(username);
      arrSocket.push(socket);
      socket.username = username;
      io.emit('NEW_USER_CONNECT', username);
      socket.emit('SERVER_ACCEPT_USERNAME',username);
    }else{
      socket.emit('SERVER_REJECT_USERNAME');
    }
    // console.log('New user: '+ username);

  };
}
module.exports = getSignup;
