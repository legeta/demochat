let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkMessOff } = require('../db.js');

let getCheckMess = (io, socket) => {
  return (object) => {
    let username1 = object.username1;
    let username2 = object.username2;
    // console.log('user: ' + username);
    checkMessOff(username1, username2, (err, result) => {
      if (err) {
        let message = '';
        socket.emit('SERVER_RETURN_MESSOFF', { username1, username2, message });
      }
      else {
        console.log('result: ' + result);
        console.log(result.rows[0].username1);
        console.log(result.rows[0].username2);
        let message = result.rows[0].message;
        socket.emit('SERVER_RETURN_MESSOFF', { username1, username2, message });
      // let mangProfile = firstname + '' + lastname + '' + age + '' + gender + '' + phone;
    }
    });
      // for (var i = 0; i < result.length; i++) {
      //   console.log(result[i]);
      // }
  };
};
module.exports = getCheckMess;
