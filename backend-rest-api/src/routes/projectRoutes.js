const { projectHandler } = require("../handlers/projectHandler");
const Joi = require("joi");

const projectRoutes = [
  {
    method: "GET",
    path: "/proyek",
    handler: projectHandler.getAllProjects,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "GET",
    path: "/proyek/kategori/{kategori}",
    handler: projectHandler.getAllProjectsByCategory,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "GET",
    path: "/proyek/status/{status}",
    handler: projectHandler.getProjectsByStatus,
    options: {
      auth: {
        strategy: "jwt",
      },
      validate: {
        params: Joi.object({
          status: Joi.string()
            .valid("terbuka", "berlangsung", "selesai")
            .required(),
        }),
      },
    },
  },
  {
    method: "GET",
    path: "/proyek/{id_proyek}",
    handler: projectHandler.getProjectsById,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "POST",
    path: "/proyek",
    handler: projectHandler.createProject,
    options: {
      auth: {
        strategy: "jwt",
      },
      payload: {
        output: "stream",
        parse: true,
        multipart: true,
        allow: "multipart/form-data",
        maxBytes: 2 * 1024 * 1024,
      },
      validate: {
        payload: Joi.object({
          nm_proyek: Joi.string().required(),
          id_kategori: Joi.number().required(),
          deskripsi: Joi.string().required(),
          file: Joi.optional(),
          tanggal_mulai: Joi.date().required(),
          tanggal_selesai: Joi.date().required(),
        }),
      },
    },
  },
  {
    method: "PUT",
    path: "/proyek/{id_proyek}",
    handler: projectHandler.updateStatusProject,
    options: {
      auth: {
        strategy: "jwt",
      },
      payload: {
        output: "stream",
        parse: true,
        multipart: true,
        allow: "multipart/form-data",
        maxBytes: 2 * 1024 * 1024,
      },
    },
  },
  {
    method: "DELETE",
    path: "/proyek/{id_proyek}",
    handler: projectHandler.deleteProjectById,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
  {
    method: "GET",
    path: "/proyek/cari/{nm_proyek}",
    handler: projectHandler.searchProjects,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
];

module.exports = projectRoutes;
