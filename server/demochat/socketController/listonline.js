let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');

let getCheckOnline = (io, socket) => {
  return () => {
    let mangOnline = '';
    for (var i = 0; i < arrUsername.length; i++) {
      if (mangOnline !== '') {
        mangOnline = mangOnline + ',' + arrUsername[i];
      }
      else {
        mangOnline = mangOnline + arrUsername[i];
      }
    }

    socket.emit('LIST_ONLINE_USER', mangOnline);
  };
};
module.exports = getCheckOnline;
