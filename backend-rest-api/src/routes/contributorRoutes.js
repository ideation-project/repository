const { contributorHandler } = require("../handlers/contributorHandler");
const Joi = require("joi");

const contributorRoutes = [
  {
    method: "GET",
    path: "/kontributor/menunggu/{id_proyek}",
    handler: contributorHandler.getAllContributorsWaiting,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "GET",
    path: "/kontributor/{id_proyek}",
    handler: contributorHandler.getContributorsByIdProject,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "POST",
    path: "/kontributor",
    handler: contributorHandler.createContributor,
    options: {
      auth: {
        strategy: "jwt",
      },
      validate: {
        payload: Joi.object({
          id_proyek: Joi.number().required(),
        }),
      },
    },
  },
  {
    method: "PUT",
    path: "/kontributor/{id_kontributor}",
    handler: contributorHandler.updateContributorStatus,
    options: {
      auth: {
        strategy: "jwt",
      },
      validate: {
        payload: Joi.object({
          status_lamaran: Joi.string().required(),
          role: Joi.string().optional(),
        }),
      },
    },
  },
];

module.exports = contributorRoutes;
