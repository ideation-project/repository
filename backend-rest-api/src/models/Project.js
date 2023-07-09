const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");
const Category = require("./Category");
const User = require("./User");
const Contributor = require("./Contributor");

const Project = sequelize.define(
  "projects",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      field: "id_proyek",
    },
    creator: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: User,
        key: "username",
      },
    },
    nm_proyek: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    id_kategori: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: Category,
        key: "id_kategori",
      },
    },
    deskripsi: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    gambar: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    tanggal_mulai: {
      type: DataTypes.DATE,
      allowNull: false,
    },
    tanggal_selesai: {
      type: DataTypes.DATE,
      allowNull: false,
    },
    status: {
      type: DataTypes.ENUM("terbuka", "sedang dikerjakan", "selesai"),
      allowNull: false,
      defaultValue: "terbuka",
    },
    total_rate: {
      type: DataTypes.FLOAT,
      allowNull: false,
      defaultValue: 0,
    },
    jumlah_raters: {
      type: DataTypes.FLOAT,
      allowNull: false,
      defaultValue: 0,
    },
    mean_rate: {
      type: DataTypes.FLOAT,
      allowNull: false,
      defaultValue: 0,
    },
    postedAt: {
      type: DataTypes.DATE,
      allowNull: false,
      defaultValue: sequelize.NOW,
    },
  },
  {
    timestamps: false, // menonaktifkan fitur timestamps
  }
);

Project.hasMany(Contributor, {
  foreignKey: "id_proyek",
  targetKey: "id",
});
Project.belongsTo(Category, { foreignKey: "id_kategori" });
Project.belongsTo(User, { foreignKey: "creator", targetKey: "username" });

module.exports = Project;
