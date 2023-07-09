const Rating = require("../models/Rating");
const { authenticateToken } = require("../config/middleware/authMiddleware");
const Project = require("../models/Project");
const User = require("../models/User");
const sequelize = require("sequelize");

const ratingHandler = {
  getAllRatings: async (request, h) => {
    try {
      // Periksa token JWT yang dikirimkan dalam header Authorization
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;

      const ratings = await Rating.findAll({
        where: {
          username: getUsernameLogin,
        },
      });

      const response = h.response({
        status: "success",
        message: "Daftar Rating berhasil ditemukan",
        ratings: ratings,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar rating",
      });
      response.code(500);
      return response;
    }
  },

  createRating: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;

      const { id_proyek, nilai } = request.payload;

      const existingRating = await Rating.findOne({
        where: { id_proyek, username: getUsernameLogin },
      });

      if (existingRating) {
        const response = h.response({
          status: "fail",
          message: "Anda sudah memberikan rating untuk proyek ini sebelumnya.",
        });
        response.code(400);
        return response;
      }

      const rating = await Rating.create({
        id_proyek,
        username: getUsernameLogin,
        nilai,
      });

      const project = await Project.findOne({ where: { id_proyek } });

      if (!project) {
        const response = h.response({
          status: "fail",
          message: "Proyek tidak ditemukan",
        });
        response.code(404);
        return response;
      }

      const newTotalNilai = project.total_rate + nilai;
      const newJumlahRaters = project.jumlah_raters + 1;
      const newRataRata = parseFloat(
        (newTotalNilai / newJumlahRaters).toFixed(1)
      );

      await Project.update(
        {
          total_rate: newTotalNilai,
          jumlah_raters: newJumlahRaters,
          mean_rate: newRataRata,
        },
        { where: { id_proyek } }
      );

      const response = h.response({
        status: "success",
        message: "Berhasil melakukan rating",
        rating: rating,
      });
      response.code(201);
      return response;
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "fail",
        message: "Gagal melakukan rating",
      });
      response.code(500);
      return response;
    }
  },

  updateRating: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;
      const { id_rating } = request.params;
      const { nilai } = request.payload;

      const existingRating = await Rating.findOne({
        where: { id_rating, username: getUsernameLogin },
      });

      if (!existingRating) {
        const response = h.response({
          status: "fail",
          message: "Rating tidak ditemukan.",
        });
        response.code(404);
        return response;
      }

      // mengubah nilai rating
      const updateProject = await Rating.update(
        { nilai: nilai },
        { where: { id_rating } }
      );

      const id_proyek = existingRating.id_proyek;

      const project = await Project.findOne({ where: { id_proyek } });
      console.log(project.total_rate);
      console.log(nilai);
      const newTotalNilai = project.total_rate + nilai;
      const newJumlahRaters = project.jumlah_raters + 1;
      const newRataRata = parseFloat(
        (newTotalNilai / newJumlahRaters).toFixed(1)
      );

      // update nilai rating di proyek
      await Project.update(
        {
          total_rate: newTotalNilai,
          jumlah_raters: newJumlahRaters,
          mean_rate: newRataRata,
        },
        { where: { id_proyek } }
      );

      const response = h.response({
        status: "success",
        message: "Berhasil melakukan rating",
      });
      response.code(200);
      return response;
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "fail",
        message: "Gagal melakukan rating",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  ratingHandler,
};
