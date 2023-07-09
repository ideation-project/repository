const Hapi = require("@hapi/hapi");
const routes = require("./routes/route");

const {
  authenticate,
  authorizeMiddleware,
} = require("./config/middleware/authMiddleware");
const HapiJwt = require("hapi-auth-jwt2");

const init = async () => {
  const server = Hapi.server({
    port: 8000,
  });

  // Registrasi plugin hapi-auth-jwt2
  await server.register(HapiJwt);

  // Menetapkan strategi autentikasi JWT
  server.auth.strategy("jwt", "jwt", authenticate);
  server.auth.default("jwt");

  // Register middleware di Hapi server
  server.ext("onPreHandler", authorizeMiddleware);

  server.route(routes);

  await server.start();
  console.log(`Server berjalan pada ${server.info.uri}`);
};

init();
