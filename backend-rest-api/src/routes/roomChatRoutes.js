const { roomChatHandler } = require("../handlers/roomChatHandler");

const roomChatRoutes = [
  {
    method: "POST",
    path: "/obrolan/{id_proyek}",
    handler: roomChatHandler.sendMessage,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "GET",
    path: "/obrolan/{id_proyek}",
    handler: roomChatHandler.getChatMessages,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
];

module.exports = roomChatRoutes;
