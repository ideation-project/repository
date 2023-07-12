const { authenticateToken } = require("../config/middleware/authMiddleware");
const Project = require("../models/Project");
const Category = require("../models/Category");
const Contributor = require("../models/Contributor");
const path = require("path");
const mime = require("mime-types");
const { nanoid } = require("nanoid");
const { Sequelize } = require("sequelize");

// cloud storage
const { Storage } = require("@google-cloud/storage");
const storage = new Storage({
  projectId: "ideation-392108",
  keyFilename: path.join(__dirname, "../../ideation-392108-a5c25924d1b2.json"),
});
const bucketName = "ideation-project-imgs";
const bucket = storage.bucket(bucketName);

// firebase
const admin = require("../config/kredensialConfig.js");

const projectHandler = {
  getAllProjects: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;

      const projects = await Project.findAll({
        include: [
          {
            model: Category,
            attributes: ["nm_kategori"],
          },
        ],
        order: [["tanggal_mulai", "DESC"]],
      });

      projects.forEach((project) => {
        project.gambar = `https://storage.googleapis.com/${bucketName}/${project.gambar}`;
      });

      const response = h.response({
        status: "success",
        message: "Daftar Project berhasil ditemukan",
        projects: projects,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar project",
      });
      response.code(500);
      return response;
    }
  },

  getAllProjectsByCategory: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const getUsernameLogin = request.auth.username;
      const { kategori } = request.params;

      const projects = await Project.findAll({
        include: [
          {
            model: Category,
            where: {
              nm_kategori: kategori,
            },
            attributes: ["nm_kategori"],
          },
        ],
      });

      projects.forEach((project) => {
        project.gambar = `https://storage.googleapis.com/${bucketName}/${project.gambar}`;
      });

      const response = h.response({
        status: "success",
        message: "Daftar Project berhasil ditemukan",
        projects: projects,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar project",
      });
      response.code(500);
      return response;
    }
  },

  getProjectsById: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;

      const getUsernameLogin = request.auth.username;

      const projects = await Project.findAll({
        where: {
          creator: getUsernameLogin,
          id_proyek: id_proyek,
        },
        include: [
          {
            model: Category,
            attributes: ["nm_kategori"],
          },
        ],
      });

      projects.forEach((project) => {
        project.gambar = `https://storage.googleapis.com/${bucketName}/${project.gambar}`;
      });

      const response = h.response({
        status: "success",
        message: "Daftar Project berhasil ditemukan",
        projects: projects,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar project",
      });
      response.code(500);
      return response;
    }
  },

  getProjectsByStatus: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { status } = request.params;

      const getUsernameLogin = request.auth.username;

      const projects = await Project.findAll({
        where: {
          status: status,
        },
        include: [
          {
            model: Contributor,
            where: {
              username: getUsernameLogin,
            },
          },
          {
            model: Category,
            attributes: ["nm_kategori"],
          },
        ],
      });
      projects.forEach((project) => {
        project.gambar = `https://storage.googleapis.com/${bucketName}/${project.gambar}`;
      });
      const response = h.response({
        status: "success",
        message: "Daftar Project berhasil ditemukan",
        projects: projects,
      });
      response.code(200);
      return response;
    } catch (error) {
      console.error(error);
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar project",
      });
      response.code(500);
      return response;
    }
  },

  createProject: async (request, h) => {
    try {
      await authenticateToken(request, h);
      const getUsernameLogin = request.auth.username;

      const { file } = request.payload;

      if (!file) {
        return h
          .response({
            status: "fail",
            message: "File tidak ditemukan.",
          })
          .code(400);
      }

      const mimeType = mime.lookup(file.hapi.filename);
      console.log(mimeType);
      if (mimeType !== "image/png" && mimeType !== "image/jpeg") {
        return h
          .response({
            status: "fail",
            message: "File harus berupa gambar PNG atau JPEG.",
          })
          .code(400);
      }

      const uniqueFilename = `${nanoid()}.${mime.extension(mimeType)}`;

      // Upload file ke Google Cloud Storage
      const uploadPath = uniqueFilename;
      const blob = bucket.file(uploadPath);

      await new Promise((resolve, reject) => {
        const blobStream = blob.createWriteStream({
          resumable: false,
          contentType: file.hapi.headers["content-type"],
        });

        file.pipe(blobStream);

        blobStream.on("finish", () => {
          console.log("File berhasil diunggah ke Google Cloud Storage.");
          resolve();
        });

        blobStream.on("error", (error) => {
          reject(error);
        });
      });

      // end untuk upload file

      const postedAt = new Date().toISOString();

      const {
        nm_proyek,
        id_kategori,
        deskripsi,
        tanggal_mulai,
        tanggal_selesai,
      } = request.payload;

      const project = await Project.create({
        creator: getUsernameLogin,
        nm_proyek,
        id_kategori,
        deskripsi,
        gambar: uniqueFilename,
        tanggal_mulai,
        tanggal_selesai,
        postedAt: postedAt,
      });

      // otomatis sebagai kontributor
      await Contributor.create({
        id_proyek: project.id,
        username: getUsernameLogin,
        role: "Creator",
        status_lamaran: "diterima",
      });

      const response = h.response({
        status: "success",
        message: "Project berhasil dibuat",
        project: project,
      });
      response.code(201);
      return response;
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "fail",
        message: "Gagal membuat project",
      });
      response.code(500);
      return response;
    }
  },

  deleteProjectById: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;
      const getUsernameLogin = request.auth.username;

      const project = await Project.findOne({
        where: {
          creator: getUsernameLogin,
          id_proyek: id_proyek,
        },
      });

      if (!project) {
        const response = h.response({
          status: "fail",
          message: "Proyek tidak ditemukan atau Anda tidak memiliki akses",
        });
        response.code(403);
        return response;
      }

      const fileName = project.gambar;
      // Hapus file terkait
      if (project.gambar !== "default.jpg") {
        const file = bucket.file(fileName);

        // Hapus file gambar lama
        await file.delete();
      }

      const deletedProject = await Project.destroy({
        where: {
          creator: getUsernameLogin,
          id_proyek: id_proyek,
        },
      });

      if (deletedProject === 0) {
        const response = h.response({
          status: "fail",
          message: "Proyek tidak berhasil dihapus",
        });
        response.code(404);
        return response;
      }

      // Menghapus room chat terkait dari Firebase Realtime Database
      const db = admin.database();
      const chatRef = db.ref(`obrolan/${id_proyek}`);
      await chatRef.remove();

      const response = h.response({
        status: "success",
        message: "Proyek berhasil dihapus",
      });
      response.code(200);
      return response;
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "fail",
        message: "Gagal menghapus proyek",
      });
      response.code(500);
      return response;
    }
  },

  updateStatusProject: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const { id_proyek } = request.params;
      const { tanggal_mulai, tanggal_selesai, deskripsi, status, nm_proyek } =
        request.payload;
      const getUsernameLogin = request.auth.username;

      // Dapatkan project berdasarkan id_proyek
      const project = await Project.findOne({
        where: {
          creator: getUsernameLogin,
          id_proyek: id_proyek,
        },
      });

      if (!project) {
        const response = h.response({
          status: "fail",
          message:
            "Project tidak ditemukan atau Anda tidak memiliki izin untuk mengubah status proyek",
        });
        response.code(403);
        return response;
      }

      // Objek untuk nilai yang diupdate
      const updatedValues = {};

      // Periksa dan atur nilai untuk setiap kolom opsional
      if (tanggal_mulai !== undefined) {
        updatedValues.tanggal_mulai = tanggal_mulai;
      }
      if (tanggal_selesai !== undefined) {
        updatedValues.tanggal_selesai = tanggal_selesai;
      }
      if (deskripsi !== undefined) {
        updatedValues.deskripsi = deskripsi;
      }
      if (status !== undefined) {
        updatedValues.status = status;
      }
      if (nm_proyek !== undefined) {
        updatedValues.nm_proyek = nm_proyek;
      }

      // Periksa dan atur gambar jika ada
      if (request.payload.file) {
        const { file } = request.payload;

        const mimeType = mime.lookup(file.hapi.filename);
        console.log(mimeType);
        if (mimeType !== "image/png" && mimeType !== "image/jpeg") {
          return h
            .response({
              status: "fail",
              message: "File harus berupa gambar PNG atau JPEG.",
            })
            .code(400);
        }
        console.log("test1");
        // Upload file ke Google Cloud Storage
        const uniqueFilename = `${nanoid()}.${mime.extension(mimeType)}`;

        // Upload file ke Google Cloud Storage
        const uploadPath = uniqueFilename;
        const blob = bucket.file(uploadPath);

        await new Promise((resolve, reject) => {
          const blobStream = blob.createWriteStream({
            resumable: false,
            contentType: file.hapi.headers["content-type"],
          });

          file.pipe(blobStream);

          blobStream.on("finish", () => {
            console.log("File berhasil diunggah ke Google Cloud Storage.");
            resolve();
          });

          blobStream.on("error", (error) => {
            reject(error);
          });
        });

        // end untuk upload file
      }

      // ini menghapus gambar
      const oldFileName = project.gambar;

      if (project.gambar !== "default.jpg") {
        const file = bucket.file(oldFileName);

        await file.delete();
      }

      // Menggunakan metode update pada model Project untuk mengubah proyek
      const [updatedCount] = await Project.update(updatedValues, {
        where: { id_proyek },
      });

      if (updatedCount > 0) {
        // Jika ada proyek yang berhasil diubah
        const response = h.response({
          status: "success",
          message: "Berhasil merubah proyek",
        });
        response.code(200);
        return response;
      } else {
        // Jika tidak ada data yang diubah atau kondisi tidak cocok
        const response = h.response({
          status: "fail",
          message: "Tidak ada perubahan",
        });
        response.code(404);
        return response;
      }
    } catch (error) {
      console.log(error);
      const response = h.response({
        status: "error",
        message: "Terjadi kesalahan server",
      });
      response.code(500);
      return response;
    }
  },

  searchProjects: async (request, h) => {
    const { nm_proyek } = request.params;
    try {
      await authenticateToken(request, h);

      const projects = await Project.findAll({
        where: {
          nm_proyek: {
            [Sequelize.Op.like]: `%${nm_proyek}%`,
          },
        },
        include: [
          {
            model: Category,
            attributes: ["nm_kategori"],
          },
        ],
      });

      projects.forEach((project) => {
        project.gambar = `https://storage.googleapis.com/${bucketName}/${project.gambar}`;
      });

      const response = h.response({
        status: "success",
        message: "Daftar Project berhasil ditemukan",
        projects: projects,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Proyek yang dicari tidak ditemukan",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  projectHandler,
};
