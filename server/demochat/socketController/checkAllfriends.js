let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkAllFriends } = require('../db.js');

let getUpdatePass = (io, socket) => {
  return (object) => {
    let username = object.userSoc;
    console.log('user: ' + username);
    checkAllFriends(username, (err, result) => {
      console.log('result: ' + result);
      console.log(result.rows[0].username1);
      // let mangFriends = [];
      let mangFriends = '';
      for (var i = 0; i < result.rowCount; i++) {
        if (result.rows[i].username1 === username) {
          // console.log('trung: '+username);
        }
        else {
          // mangFriends.push(result.rows[i].username1);
          mangFriends = mangFriends + ','+result.rows[i].username1;
        }
        if (result.rows[i].username2 === username) {
        }
        else {
          // mangFriends.push(result.rows[i].username2);
          mangFriends = mangFriends + ','+result.rows[i].username2;
        }
      }
      socket.emit('SERVER_RETURN_FRIENDS', mangFriends);
      console.log(mangFriends);
      console.log('mangFriends: ' + mangFriends.toString());
    });
      // for (var i = 0; i < result.length; i++) {
      //   console.log(result[i]);
      // }
  };
};
module.exports = getUpdatePass;
