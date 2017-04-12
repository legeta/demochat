let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { deleteFriends } = require('../db.js');

let getdeleteFriends = (io, socket) => {
  return (object) => {
    let username1 = object.username1;
    let username2 = object.username2;
    console.log('user_delete: ' + username2);
    deleteFriends(username1, username2, (err, result) => {
      if (err) return socket.emit('SERVER_ERR', err);
      socket.emit('SERVER_DELETE_FRIEND', { username2 });
      console.log(username2.toString());
    });
  };
};
module.exports = getdeleteFriends;
