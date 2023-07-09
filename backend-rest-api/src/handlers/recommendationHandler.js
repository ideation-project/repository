const { authenticateToken } = require("../config/middleware/authMiddleware");
const Recommendation = require("../models/Recommendation");
const Project = require("../models/Project");
const Category = require("../models/Category");
const User = require("../models/User");

const recommendationHandler = {
  getRecommendations: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;

      const recommendations = await Recommendation.findAll({
        include: [
          {
            model: User,
            attributes: ["id_user", "username"],
            where: {
              username: getUsernameLogin,
            },
          },
          {
            model: Project,
            include: [
              {
                model: Category,
              },
            ],
          },
        ],
        order: [["id_rekomendasi", "ASC"]],
      });

      recommendations.forEach((recommendation) => {
        recommendation.project.gambar = `https://storage.googleapis.com/project-imgs/${recommendation.project.gambar}`;
      });

      const response = h.response({
        status: "success",
        message: "Rekomendasi ditemukan",
        recommendations: recommendations,
      });

      response.code(200);
      return response;
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil rekomendasi",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  recommendationHandler,
};
