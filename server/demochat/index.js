let express = require('express');
let app = express();
let parser = require('body-parser').urlencoded({ extended: false });
let server = require('http').createServer(app);//1
let io = require('socket.io').listen(server);//2
let fs = require('fs');
let arrUsername = require('./socketController/arrUsername.js');
let arrSocket = require('./socketController/arrSocket.js');
let { checkUser, insertUser, insertOffMess, checkUserExist, UpdateOffMess, checkMessOff } = require('./db.js');

server.listen(process.env.PORT || 3000, () => console.log('Server started'));//3

app.set('view engine', 'ejs');
app.set('views', './views');
app.use(express.static('public'));
app.get('/', (req, res) => res.render('home'));
app.get('/dangnhap', (req, res) => res.render('dangnhap'));

app.get('/dangky', (req, res) => res.render('dangky'));

app.post('/xulydangky', parser, (req, res) => {
  let { username, password, phone } = req.body;
  insertUser(username, password, phone, (err, result) => {
    if (err) return res.send(err);
    if (result.rowCount != 1) return res.send('Loi dang ky');
    res.send('Dang ky thanh cong');
  });
});

app.post('/xulydangnhap', parser, (req, res) => {
  let { username, password } = req.body;
  checkUser(username, password, err => {
    if (err) return res.send(err + '');
    res.send('Dang nhap thanh cong');
  });
});

io.on('connection', socket => {
  console.log('Co nguoi ket noi');
  socket.on('CLIENT_SIGN_UP', require('./socketController/signup.js')(io, socket));
  socket.on('CLIENT_SIGN_IN', require('./socketController/signin.js')(io, socket));
  socket.on('ADD_FRIENDS', require('./socketController/addfriends.js')(io, socket));
  socket.on('UPDATE_PASS', require('./socketController/updatepass.js')(io, socket));
  socket.on('UPDATE_PROFILE', require('./socketController/updateprofile.js')(io, socket));
  socket.on('CHECK_FRIENDS', require('./socketController/checkAllfriends.js')(io, socket));
  socket.on('CHECK_PROFILE', require('./socketController/checkAllProfile.js')(io, socket));
  socket.on('DELETE_FRIENDS', require('./socketController/deletefriends.js')(io, socket));
  socket.on('CHECK_MESSAGE_OFF', require('./socketController/checkmessoff.js')(io, socket));
  // socket.on('CHECK_ONLINE', require('./socketController/listonline.js')(io, socket));

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

  socket.on('disconnect', () => {
    io.emit('USER_DISCONNECTED', socket.username);
    console.log(arrUsername);
    arrUsername.splice(arrUsername.indexOf(socket.username), 1);
    arrSocket.splice(arrSocket.indexOf(socket), 1);
  });
  socket.on('CLIENT_SEND_MESSAGE', data => {
    let desSocket = arrSocket.find(soc => soc.username === data.des);
    let mess = data.msg;
    let usersend = data.des+'';
    let myusername = socket.username+'';
    console.log('myuser: '+myusername+' usernhan: '+usersend);
    if (desSocket) {
      // let mess = socket.username + ': ' + data.msg;
      console.log('mess_send:' + mess);
      desSocket.emit('RECEIVE_NEW_MESSAGE', { mess, usersend });
    }else {
      // socket.emit('ERROR_USER');
      let messa = myusername + ': ' + data.msg;
      insertOffMess(myusername, usersend, messa, (err, result) => {
        let loiInsertF = err + '';
        console.log('LoiIn: ' + loiInsertF);
        if (loiInsertF === 'error: duplicate key value violates unique constraint "Offline_message_pkey"') {
          checkMessOff(myusername, usersend, (err, result) => {
            let msg = result.rows[0].message;
            let newmsg = msg + '\n' + messa;
            UpdateOffMess(myusername, usersend, newmsg, (err, result) => {
              let loiUpMess = err + '';
              console.log('loiUpMess: ' + loiUpMess);
              if (err) return socket.emit('SERVER_ERR', loiInsertF);
              if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
            });
        });
        } else {
          if (err) return socket.emit('SERVER_ERR', loiInsertF);
          if (result.rowCount != 1) return socket.emit('SERVER_RETURN_ERR', 'Không thành công');
        }
        socket.emit('SERVER_INSERT_OFFLINE_MESS', { myusername, usersend, mess });
        console.log(myusername + ' send to ' + usersend + ' Offline_message: ' + mess);
      });
    }
    // console.log(data);
  });
});
