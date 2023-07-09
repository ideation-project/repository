const path = require("path");
const { authenticateToken } = require("../config/middleware/authMiddleware");
const Contributor = require("../models/Contributor");
const admin = require("../config/kredensialConfig.js");

const roomChatHandler = {
  sendMessage: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { message } = request.payload;

      const getUsernameLogin = request.auth.username;
      const { id_proyek } = request.params;

      // periksa jika user kontributor
      const existingContributor = await Contributor.findOne({
        where: {
          id_proyek,
          username: getUsernameLogin,
        },
      });

      if (existingContributor) {
        const db = admin.database();
        const ref = db.ref(`obrolan/${id_proyek}`);

        // Membuat key baru untuk pesan
        const newChatRef = ref.push();
        const chatId = newChatRef.key;

        // Menyimpan data pesan ke Firebase Realtime Database
        await newChatRef.set({
          id_proyek: id_proyek,
          username: getUsernameLogin,
          message,
        });

        const response = h.response({
          status: "success",
          message: "Pesan berhasil dikirim",
          chatId,
        });
        response.code(201);
        return response;
      } else {
        const response = h.response({
          status: "fail",
          message: "Anda tidak memiliki izin mengakses room chat",
        });
        response.code(401);
        return response;
      }
    } catch (error) {
      console.error("Gagal mengirim pesan:", error);
      const response = h.response({
        status: "fail",
        message: "Gagal mengirim pesan",
      });
      response.code(500);
      return response;
    }
  },

  getChatMessages: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;
      const getUsernameLogin = request.auth.username;
      // periksa jika user kontributor
      const existingContributor = await Contributor.findOne({
        where: {
          id_proyek,
          username: getUsernameLogin,
        },
      });

      if (existingContributor) {
        const db = admin.database();
        const ref = db.ref(`obrolan/${id_proyek}`);

        const snapshot = await ref.once("value");
        const messages = snapshot.val();

        // Memeriksa apakah ada data pesan
        if (messages) {
          // Mengubah pesan-pesan menjadi array
          const messageArray = Object.keys(messages).map((key) => {
            return {
              messageId: key,
              ...messages[key],
            };
          });

          const response = h.response({
            status: "success",
            messages: messageArray,
          });
          response.code(200);
          return response;
        } else {
          const response = h.response({
            status: "success",
            messages: [],
          });
          response.code(200);
          return response;
        }
      } else {
        const response = h.response({
          status: "fail",
          message: "Anda tidak memiliki izin mengakses room chat",
        });
        response.code(401);
        return response;
      }
    } catch (error) {
      console.error("Gagal mendapatkan pesan:", error);
      const response = h.response({
        status: "fail",
        message: "Gagal mendapatkan pesan",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  roomChatHandler,
};
