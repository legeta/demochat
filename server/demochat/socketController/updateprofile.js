let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { updateProfile, checkUserPass } = require('../db.js');

let getUpdateProfile = (io, socket) => {
  return (object) => {
    let username = object.username;
    let firstname = object.firstname;
    let lastname = object.lastname;
    let age = object.age;
    let gender = object.gender;
    let phone = object.phone;
    let password = object.password;
    console.log('old pass: ' + password);
    checkUserPass(username, password, (err) => {
      // console.log(username);
      let errPass = err + '';
      console.log(errPass);
      if (errPass == 'Error: Check your password') {
        socket.emit('SERVER_ERR', errPass);
      }
      else {
        updateProfile(username, firstname, lastname, age, gender, phone, (err, result) => {
          let loiEdit = err + '';
          // if (loiInsertF == 'error: duplicate key value violates unique constraint "Friends_pkey"') {
          //   loiInsertF = 'You and ' + username2 + ' have been friend';
          // }
          console.log('LoiUpprofile: ' + loiEdit);

          if (err) return socket.emit('SERVER_ERR', loiEdit);
          // if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
          socket.emit('SERVER_UPDATE_PROFILE', 'Update profile successful');
        });
      }
    });
      // console.log('New user: ' + username);
      // console.log('New user: '+ password);
  };
};
module.exports = getUpdateProfile;
