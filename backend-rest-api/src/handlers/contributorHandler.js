const Contributor = require("../models/Contributor");
const Project = require("../models/Project");
const User = require("../models/User");
const { authenticateToken } = require("../config/middleware/authMiddleware");

const contributorHandler = {
  getAllContributorsWaiting: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;
      const getUsernameLogin = request.auth.username;

      // Mengambil semua kategori dari database
      const contributors = await Contributor.findAll({
        where: {
          id_proyek: id_proyek,
          status_lamaran: "menunggu",
        },
      });

      const response = h.response({
        status: "success",
        message: "Daftar Kontributor berhasil ditemukan",
        contributors: contributors,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar kontributor",
      });
      response.code(500);
      return response;
    }
  },

  createContributor: async (request, h) => {
    try {
      await authenticateToken(request, h);
      const getUsernameLogin = request.auth.username;

      const { id_proyek } = request.payload;
      console.log(id_proyek);
      console.log(getUsernameLogin);
      // Cek apakah username sudah terdaftar sebagai kontributor pada proyek tersebut
      const existingContributor = await Contributor.findOne({
        where: {
          id_proyek,
          username: getUsernameLogin,
        },
      });

      // Jika username sudah terdaftar, tampilkan pesan error
      if (existingContributor) {
        const response = h.response({
          status: "fail",
          message: "Anda sudah mendaftar sebagai kontributor!",
        });
        response.code(400);
        return response;
      }
      console.log(getUsernameLogin);

      const contributor = await Contributor.create({
        id_proyek,
        username: getUsernameLogin,
      });

      const response = h.response({
        status: "success",
        message: "Kontributor Berhasil Mendaftar",
        contributor: contributor,
      });
      response.code(201);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Kontributor Gagal Mendaftar",
      });
      response.code(500);
      return response;
    }
  },

  updateContributorStatus: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_kontributor } = request.params;
      const { status_lamaran, role } = request.payload;
      const getUsernameLogin = request.auth.username;

      // Dapatkan kontributor berdasarkan id_kontributor
      const contributor = await Contributor.findOne({
        where: { id_kontributor },
      });
      console.log(id_kontributor);
      if (!contributor) {
        const response = h.response({
          status: "fail",
          message: "Kontributor tidak ditemukan",
        });
        response.code(404);
        return response;
      }

      // Dapatkan nilai id_proyek dari kontributor
      const id_proyek = contributor.id_proyek;

      const project = await Project.findOne({ where: { id_proyek } });

      if (!project) {
        const response = h.response({
          status: "fail",
          message: "Proyek tidak ditemukan",
        });
        response.code(404);
        return response;
      }

      // Dapatkan ID pembuat proyek dari properti proyek yang sesuai
      const creator = project.creator;

      // Periksa apakah pengguna yang melakukan permintaan adalah pembuat proyek yang sesuai
      if (creator !== getUsernameLogin) {
        const response = h.response({
          status: "fail",
          message: "Anda tidak memiliki izin untuk mengubah status kontributor",
        });
        response.code(403);
        return response;
      }

      let message;
      if (status_lamaran === "diterima") {
        message = "Lamaran berhasil diterima!";
      } else if (status_lamaran === "ditolak") {
        message = "Maaf, Lamaran Anda ditolak.";
      }

      // Menggunakan metode update pada model Contributor untuk mengubah field status
      const updatedContributor = await Contributor.update(
        { status_lamaran: status_lamaran, role: role },
        { where: { id_kontributor } }
      );

      if (updatedContributor[0] === 1) {
        console.log("yepi");
        // Jika ada satu kontributor yang berhasil diubah
        const response = h.response({
          status: "success",
          message,
        });
        response.code(200);
        return response;
      } else {
        // Jika tidak ada kontributor yang diubah atau kondisi tidak cocok
        const response = h.response({
          status: "fail",
          message: "Tidak ada perubahan",
        });
        response.code(404);
        return response;
      }
    } catch (error) {
      const response = h.response({
        status: "error",
        message: "Terjadi kesalahan server",
      });
      response.code(500);
      return response;
    }
  },

  getContributorsByIdProject: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;
      const getUsernameLogin = request.auth.username;

      const contributors = await Contributor.findAll({
        where: {
          id_proyek: id_proyek,
          status_lamaran: "diterima",
        },
        include: [
          {
            model: User,
            attributes: ["name"],
            as: "user",
          },
        ],
      });

      const contributorsWithNames = contributors.map((contributor) => {
        return {
          username: contributor.username,
          name: contributor.user.name,
          role: contributor.role,
        };
      });

      const response = h.response({
        status: "success",
        message: "Daftar Kontributor berhasil ditemukan",
        contributors: contributorsWithNames,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar kontributor",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  contributorHandler,
};
