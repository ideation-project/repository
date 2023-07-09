const { recommendationHandler } = require("../handlers/recommendationHandler");
const Joi = require("joi");

const recommendationRoutes = [
  {
    method: "GET",
    path: "/rekomendasi",
    handler: recommendationHandler.getRecommendations,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
];

module.exports = recommendationRoutes;
