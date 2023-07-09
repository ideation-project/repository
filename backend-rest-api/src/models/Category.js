const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");

const Category = sequelize.define(
  "categories",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      field: "id_kategori", // menentukan nama kolom di database
    },
    nm_kategori: {
      type: DataTypes.STRING,
      allowNull: false,
    },
  },
  {
    timestamps: false, // menonaktifkan fitur timestamps
  }
);

module.exports = Category;
