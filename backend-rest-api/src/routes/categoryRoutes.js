const { categoryHandler } = require("../handlers/categoryHandler");

const categoryRoutes = [
  {
    method: "GET",
    path: "/kategori",
    handler: categoryHandler.getAllCategories,
    options: {
      auth: {
        strategy: "jwt",
      },
    },
  },
];

module.exports = categoryRoutes;
