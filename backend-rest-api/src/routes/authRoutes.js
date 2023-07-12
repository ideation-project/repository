const { authHandler } = require("../handlers/authHandler");
const Joi = require("joi");

const authRoutes = [
  {
    method: "GET",
    path: "/",
    handler: (request, h) => {
      const message = "Ideation Backend Rest API!";
      return h.response({ message });
    },
    options: {
      auth: false,
    },
  },
  {
    method: "POST",
    path: "/login",
    handler: authHandler.login,
    options: {
      validate: {
        payload: Joi.object({
          username: Joi.string().required(),
          password: Joi.string().required(),
        }),
      },
      auth: false,
    },
  },
  {
    method: "POST",
    path: "/register",
    handler: authHandler.register,
    options: {
      validate: {
        payload: Joi.object({
          username: Joi.string().required(),
          password: Joi.string().required(),
          name: Joi.string().required(),
          email: Joi.string().email().required(),
          pref_categories: Joi.string().required(),
        }),
      },
      auth: false,
    },
  },
  {
    method: "GET",
    path: "/profil/{username}",
    handler: authHandler.getProfilByUsername,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "POST",
    path: "/logout",
    handler: authHandler.logout,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
];

module.exports = authRoutes;
