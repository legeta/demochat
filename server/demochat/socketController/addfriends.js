let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { insertFriends, checkUserExist } = require('../db.js');

let getAddfirends = (io, socket) => {
  return (object) => {
    let username1 = object.username1;
    let username2 = object.username2;
    console.log('user: ' + username1);
    console.log('Friends: ' + username2);
    checkUserExist(username2, (err, result) => {
      let friendOk = err + '';
      // console.log('check: '+friendOk);
      if (friendOk == 'Error: Username already exists') {
        console.log('check: '+friendOk);
        insertFriends(username1, username2, (err, result) => {
          let loiInsertF = err + '';
          if (loiInsertF == 'error: duplicate key value violates unique constraint "Friends_pkey"') {
            loiInsertF = 'You and ' + username2 + ' have been friend';
          }
          console.log('LoiIn: ' + loiInsertF);

          if (err) return socket.emit('SERVER_ERR', loiInsertF);
          if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
          socket.emit('SERVER_INSERT_FRIENDS_OK', { username1, username2 });
          console.log('User: ' + username1 + ' and user:' + username2 + ' are friends');
        });
      }
      else {
        return socket.emit('SERVER_ERR', 'User does not exist');
      }
    });
      // console.log('New user: ' + username);
      // console.log('New user: '+ password);
  };
};
module.exports = getAddfirends;
