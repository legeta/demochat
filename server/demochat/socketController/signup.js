let arrUsername = require('./arrUsername.js');
let arrSocket = require('./arrSocket.js');
let { checkUser, insertUser, checkSignup } = require('../db.js');

let getSignup = (io, socket) => {
  return (object) => {
    let username = object.username;
    let password = object.password;
    let firstname = object.firstname;
    let lastname = object.lastname;
    let age = object.age;
    let gender = object.gender;
    let phone = object.phone;
    console.log('userdk: ' + username);
    console.log('passdk: ' + password);
    console.log(firstname);
    console.log(lastname);
    console.log(gender);
    console.log(age);
    console.log('phonedk: ' + phone);

    checkSignup(username, error => {
      if (error) {
        let loiDk = ''+error;
        console.log(loiDk);
        socket.emit('SERVER_SIGNUP_ERR', loiDk);
      } else {
        insertUser(username, password, firstname, lastname, age, gender, phone, (err, result) => {
          let loiInsert = err + '';
          console.log('LoiIn: ' + loiInsert);
          if (err) return socket.emit('SERVER_ERR', loiInsert);
          if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
          socket.emit('SERVER_INSERT_OK', { username, password });
          console.log('New userdk: ' + username + ' pass:' + password);
        });
        // console.log('New user: ' + username);
        // console.log('New user: '+ password);
      }
    });
  };
};
module.exports = getSignup;
