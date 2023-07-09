const Category = require("../models/Category");
const jwt = require("jsonwebtoken");
const { authenticateToken } = require("../config/middleware/authMiddleware");

const categoryHandler = {
  getAllCategories: async (request, h) => {
    try {
      await authenticateToken(request, h);

      const categories = await Category.findAll();

      const response = h.response({
        status: "success",
        message: "Daftar kategori berhasil ditemukan",
        categories: categories,
      });
      response.code(200);
      return response;
    } catch (error) {
      const response = h.response({
        status: "fail",
        message: "Gagal mengambil daftar kategori",
      });
      response.code(500);
      return response;
    }
  },
};

module.exports = {
  categoryHandler,
};
