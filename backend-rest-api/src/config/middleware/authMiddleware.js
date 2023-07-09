const jwt = require("jsonwebtoken");

const secretKey = "iin!alahad@iatdike#ka3tB0a1B2icgnth2";

const validate = async (decoded, request, h) => {
  return { isValid: true };
};

const authenticate = {
  key: secretKey,
  verify: true,
  validate,
  verifyOptions: { algorithms: ["HS256"] },
};

// Fungsi otorisasi untuk memeriksa token JWT
const authorizeUser = (token, request) => {
  try {
    const decodedToken = jwt.verify(token, secretKey);
    const username = decodedToken.username;
    request.auth.username = username;
    return true; // Otorisasi berhasil
  } catch (error) {
    return false; // Otorisasi gagal
  }
};

// Middleware untuk otorisasi
const authenticateToken = async (request, h) => {
  try {
    const authHeader = request.headers.authorization;
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      const response = h.response({
        status: "fail",
        message: "Unauthorized",
      });
      response.code(401);
      return response;
    }

    const token = authHeader.substring(7);

    // Periksa otorisasi menggunakan fungsi authorizeUser
    const isAuthorized = await authorizeUser(token, request);
    if (!isAuthorized) {
      const response = h.response({
        status: "fail",
        message: "Token tidak valid",
      });
      response.code(401);
      return response;
    }

    // Lanjutkan eksekusi ke handler berikutnya
    return h.continue;
  } catch (error) {
    // Tangani kesalahan jika terjadi
    console.error("Error in authenticateToken:", error);
    const response = h.response({
      status: "error",
      message: "Internal Server Error",
    });
    response.code(500);
    return response;
  }
};

module.exports = {
  secretKey,
  authenticate,
  authorizeUser,
  authenticateToken,
};
