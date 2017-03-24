let express = require('express');
let app = express();
let parser = require('body-parser').urlencoded({extended: false});
let {checkUser, insertUser} = require('./db.js');
let server = require('http').Server(app);//1
let io = require('socket.io')(server);//2
let arrUsername = require('./socketController/arrUsername.js');
let arrSocket = require('./socketController/arrSocket.js');

server.listen(3000, () => console.log('Server started'));//3

app.set('view engine', 'ejs');
app.set('views', './views');
app.use(express.static('public'));
app.get('/', (req, res) => res.render('home'));
app.get('/dangnhap', (req, res) => res.render('dangnhap'));

app.get('/dangky', (req, res) => res.render('dangky'));

app.post('/xulydangnhap', parser, (req, res) => {
  let {username, password} = req.body;
  checkUser(username, password, err => {
    if(err) return res.send(err + '');
    res.send('Dang nhap thanh cong');
  });
});

app.post('/xulydangky', parser, (req, res) => {
  let {username, password, phone} = req.body;
  insertUser(username, password, phone, (err, result) => {
    if(err) return res.send(err);
    if(result.rowCount != 1) return res.send('Loi dang ky');
    res.send('Dang ky thanh cong');
  });
});


io.on('connection', socket => {
  console.log('Co nguoi ket noi');
  socket.emit('LIST_ONLINE_USER',arrUsername);
  socket.on('CLIENT_SIGN_UP', require('./socketController/signup.js')(io, socket));
  socket.on('disconnect', () => {
    io.emit('USER_DISCONNECTED', socket.username);
    arrUsername.splice(arrUsername.indexOf(socket.username),1);
    arrSocket.splice(arrSocket.indexOf(socket),1);
  });
  socket.on('CLIENT_SEND_MESSAGE', data => {
    let desSocket = arrSocket.find( soc => soc.username === data.des);
    if (desSocket) {
      let mess = socket.username + ': '+data.msg
      desSocket.emit('RECEIVE_NEW_MESSAGE', mess);
    }else {
      socket.emit('ERROR_USER');
    }
    // console.log(data);
  });
});
