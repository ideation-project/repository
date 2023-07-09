const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");
const Project = require("./Project");
const User = require("./User");

const Rating = sequelize.define(
  "ratings",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      field: "id_rating",
    },
    id_proyek: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: Project,
        key: "id_proyek",
      },
    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      references: {
        model: User,
        key: "username",
      },
    },
    nilai: {
      type: DataTypes.INTEGER,
      allowNull: false,
      defaultValue: 0,
    },
  },
  // {
  //   timestamps: true, 
  // }
);

Rating.belongsTo(Project, { foreignKey: "id_proyek" });
Rating.belongsTo(User, { foreignKey: "username", targetKey: "username" });
module.exports = Rating;
