const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");
const User = require("./User");

const Contributor = sequelize.define(
  "contributors",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      field: "id_kontributor",
    },
    id_proyek: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      references: {
        model: User,
        key: "username",
      },
    },
    role: {
      type: DataTypes.STRING,
      allowNull: false,
      defaultValue: "-",
    },
    status_lamaran: {
      type: DataTypes.ENUM("menunggu", "ditolak", "diterima"),
      allowNull: false,
      defaultValue: "menunggu",
    },
  },
  {
    timestamps: false,
  }
);

// Contributor.belongsTo(Project);

Contributor.belongsTo(User, { foreignKey: "username", targetKey: "username" });

module.exports = Contributor;
