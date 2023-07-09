const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");
const Project = require("./Project");
const User = require("./User");

const Recommendation = sequelize.define(
  "recommendations",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      field: "id_rekomendasi", // menentukan nama kolom di database
    },
    id_user: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: User,
        key: "id_user",
      },
    },
    id_project: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: Project,
        key: "id_proyek",
      },
    },
    project_title: {
      type: DataTypes.STRING,
      allowNull: false,
    },
  },
  {
    timestamps: false, // menonaktifkan fitur timestamps
  }
);

Recommendation.belongsTo(Project, { foreignKey: "id_project" });
Recommendation.belongsTo(User, { foreignKey: "id_user" });

module.exports = Recommendation;
