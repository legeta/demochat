var pg = require('pg');
let {decrypt, encrypt} = require('./crypto.js');

var config = {
  user: 'postgres',
  database: 'ChatDemo',
  password: '123456',
  host: 'localhost',
  port: 5432,
  max: 10,
  idleTimeoutMillis: 1000
};

var pool = new pg.Pool(config);

function query(sql, data, cb) {
  pool.connect((err, client, done) => {
    if (err) return cb(err);
    done();
    client.query(sql, data, (err, result) => {
      if (err) return cb(err)
      //console.log(result.rows);
      cb(undefined, result);
    });
  });
}

let insertUser = (username, password, firstname, lastname, age, gender, phone, cb) => {
  let sql = `INSERT INTO public."User"(username, "password", firstname, lastname, age, gender, phone)
	VALUES ($1, $2, $3, $4, $5, $6, $7)`;
  query(sql, [username, encrypt(password), firstname, lastname, age, gender, phone], cb);
}

let checkUser = (username, password, cb) => {
  let sql = `SELECT * FROM "User" WHERE username = $1`;
  query(sql, [username], (err, result) => {
    if (err) return cb(err);
    if (result.rowCount != 1) return cb(new Error('Username does not exist'));
    if (password != decrypt(result.rows[0].password)) {
      return cb(new Error('Check your username or password'));
    }
    cb(undefined);
  });
};

let checkSignup = (username, cb) => {
  let sql = `SELECT * FROM "User" WHERE username = $1`;
  query(sql, [username], (err, result) => {
    if (err) return cb(err);
    if (result.rowCount == 1) return cb(new Error('Username already exists'));
    cb(undefined);
  });
};

let checkUserExist = (username, cb) => {
  let sql = `SELECT * FROM "User" WHERE username = $1`;
  query(sql, [username], (err, result) => {
    if (err) return cb(err);
    if (result.rowCount == 1) return cb(new Error('Username already exists'));
    cb(undefined);
  });
};

let checkUserPass = (username, password, cb) => {
  let sql = `SELECT * FROM "User" WHERE username = $1`;
  query(sql, [username], (err, result) => {
    if (err) return cb(err);
    if (password != decrypt(result.rows[0].password)) {
      return cb(new Error('Check your password'));
    }
    cb(undefined);
  });
};

let insertFriends = (username1, username2, cb) => {
  let sql = `INSERT INTO public."Friends"(username1, username2)
	VALUES ($1, $2)`;
  query(sql, [username1, username2], cb);
}

let updatePass = (username, password , cb) => {
  let sql = `UPDATE public."User" SET password = $1 WHERE username = $2`;
  query(sql, [encrypt(password), username], cb);
}

let updateProfile = (username, firstname, lastname, age, gender, phone, cb) => {
  let sql = `UPDATE public."User"
	SET firstname=$1, lastname=$2, age=$3, gender=$4, phone=$5
	WHERE username=$6;`;
  query(sql, [firstname, lastname, age, gender, phone, username], cb);
}

function checkAllFriends(username, cb) {
  let sql = `SELECT * FROM "Friends" WHERE username1 = $1 OR username2 = $1`;
  query(sql, [username], (err, result) => {
    cb(undefined, result);
  });
  // console.log(query(sql, [username]) + '');
  //  => {
  //   if (err) return cb(err);
  //   if (result.rowCount == 0) return cb(undefined);
  //   // console.log(result.rows[0].username2.toString() + 'result query');
  //   console.log(result.toString() + 'result query');
  //   // return (result);
  //   // cb(undefined);
  // });
};

function checkAllProfile(username, cb) {
  let sql = `SELECT * FROM "User" WHERE username = $1`;
  query(sql, [username], (err, result) => {
    cb(undefined, result);
  });
}

function deleteFriends(username1, username2, cb) {
  let sql = `DELETE FROM public."Friends" WHERE (username1 = $1 AND username2 = $2) OR (username1 = $2 AND username2 = $1)`;
  query(sql, [username1, username2], (err, result) => {
    cb(undefined, result);
  });
}

module.exports = { checkUser, insertUser, checkSignup, insertFriends, checkUserExist, updatePass, checkUserPass, checkAllFriends, updateProfile, checkAllProfile, deleteFriends };
