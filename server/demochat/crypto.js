let CryptoJS = require('crypto-js');
const secret = 'HG83249jjbdsafjkK*(*dja-==)';

// var ciphertext = CryptoJS.AES.encrypt('my message', 'secret key 123');
//
// // console.log(ciphertext.toString());
//
// var bytes  = CryptoJS.AES.decrypt(ciphertext.toString(), 'secret key 123');
// var plaintext = bytes.toString(CryptoJS.enc.Utf8);
// console.log(plaintext);

let encrypt = (password) => {
  let obj = CryptoJS.AES.encrypt(password, secret);
  return obj.toString();
}

let decrypt = (en) => {
  let bytes  = CryptoJS.AES.decrypt(en, secret);
  return bytes.toString(CryptoJS.enc.Utf8);
}

// let enc = encrypt('KhoaPham 123 123');
// console.log('Chuoi ma hoa',enc);
// console.log('Chuoi da giai ma', decrypt(enc));

module.exports = {encrypt, decrypt};
//chuc nang dang ky va dang nhap
//username, password, sdt
//
