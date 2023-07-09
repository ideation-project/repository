const authRoutes = require("./authRoutes");
const categoryRoutes = require("./categoryRoutes");
const projectRoutes = require("./projectRoutes");
const contributorRoutes = require("./contributorRoutes");
const ratingRoutes = require("./ratingRoutes");
const recommendationRoutes = require("./recommendationRoutes");
const roomChatRoutes = require("./roomChatRoutes");

const routes = [
  ...authRoutes,
  ...categoryRoutes,
  ...projectRoutes,
  ...contributorRoutes,
  ...ratingRoutes,
  ...recommendationRoutes,
  ...roomChatRoutes,
];

module.exports = routes;
