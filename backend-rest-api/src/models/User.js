const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");

const User = sequelize.define(
  "users",
  {
    id: {
      type: DataTypes.STRING,
      primaryKey: true,
      autoIncrement: true,
      field: "id_user", // menentukan nama kolom di database
    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    password: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    pref_categories: {
      type: DataTypes.STRING,
      allowNull: true,
    },
  },
  {
    timestamps: false, // menonaktifkan fitur timestamps
  }
);

module.exports = User;
