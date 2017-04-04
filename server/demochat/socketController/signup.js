let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkUser, insertUser } = require('../db.js');

let getSignup = (io, socket) => {
  return (object) => {
    let username = object.username;
    let password = object.password;
    let phone = object.phone;
    console.log('userdk: ' + username);
    console.log('passdk: ' + password);
    console.log('phonedk: ' + phone);

    checkUser(username, password, error => {
      if (error) {
        insertUser(username, password, phone, (err, result) => {
          if (err) return socket.emit('SERVER_ERR', err);
          if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
          socket.emit('SERVER_INSERT_OK', { username, password });
        });
        console.log('New userdk: ' + username);
        // console.log('New user: ' + username);
        // console.log('New user: '+ password);
      } else {
        socket.emit('SERVER_SIGNUP_ERR', error);
      }
    });
  };
};
module.exports = getSignup;
