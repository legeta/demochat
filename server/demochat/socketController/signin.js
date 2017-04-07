let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkUser, insertUser } = require('../db.js');

let getSignin = (io, socket) => {
  return (object) => {
    let username = object.username;
    let password = object.password;
    console.log(username);
    console.log('pass: ' + password);

    checkUser(username, password, err => {
      if (err) {
        let error = err + ' ';
        console.log(error);
        socket.emit('SERVER_RETURN_ERR', error);
      } else {
        if (arrUsername.indexOf(username) === -1) {
          arrUsername.push(username);
          arrSocket.push(socket);
          socket.username = username;

          io.emit('NEW_USER_CONNECT', username);
          socket.emit('SERVER_ACCEPT_USERNAME', username);
        } else {
          socket.emit('SERVER_REJECT_USERNAME');
        }
        console.log('New user: ' + username);
        // console.log('New user: '+ password);
      }
    });
    socket.emit('LIST_ONLINE_USER', { danhsach: arrUsername });
  };
};

module.exports = getSignin;
