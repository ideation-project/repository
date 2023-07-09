// kredensial untuk firebase
const admin = require("firebase-admin");
const serviceAccount = require("../../firebase-adminsdk-ltd4t-64bcc1cae5.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://room-chat-idekita.asia-southeast1.firebasedatabase.app",
});

module.exports = admin;
