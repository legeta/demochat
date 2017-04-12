let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkAllProfile } = require('../db.js');

let getUpdatePass = (io, socket) => {
  return (object) => {
    let username = object.userSoc;
    console.log('user: ' + username);
    checkAllProfile(username, (err, result) => {
      console.log('result: ' + result);
      console.log(result.rows[0].username);
      let firstname = result.rows[0].firstname;
      let lastname = result.rows[0].lastname;
      let age = result.rows[0].age;
      let gender = result.rows[0].gender;
      let phone = result.rows[0].phone;
      socket.emit('SERVER_RETURN_PROFILE', { firstname, lastname, age, gender, phone });
    });
      // for (var i = 0; i < result.length; i++) {
      //   console.log(result[i]);
      // }
  };
};
module.exports = getUpdatePass;
