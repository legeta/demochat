let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { updatePass, checkUserPass } = require('../db.js');

let getUpdatePass = (io, socket) => {
  return (object) => {
    let username = object.username;
    let oldpassword = object.oldpassword;
    let newpassword = object.newpassword;
    console.log('old pass: ' + oldpassword);
    checkUserPass(username, oldpassword, (err) => {
      // console.log(username);
      let errPass = err + '';
      console.log(errPass);
      if (errPass == 'Error: Check your password') {
        socket.emit('SERVER_ERR', errPass);
      }
      else {
        updatePass(username, newpassword, (err, result) => {
          let loiEdit = err + '';
          // if (loiInsertF == 'error: duplicate key value violates unique constraint "Friends_pkey"') {
          //   loiInsertF = 'You and ' + username2 + ' have been friend';
          // }
          console.log('LoiUppass: ' + loiEdit);

          if (err) return socket.emit('SERVER_ERR', loiEdit);
          // if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
          socket.emit('SERVER_UPDATE_OK', 'Update password successful');
        });
      }
    });
      // console.log('New user: ' + username);
      // console.log('New user: '+ password);
  };
};
module.exports = getUpdatePass;
