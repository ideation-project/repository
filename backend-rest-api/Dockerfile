# Gunakan image Node.js versi terbaru sebagai base image
FROM node:18

# Tentukan direktori kerja di dalam container
WORKDIR /app/src

# Salin package.json dan package-lock.json ke direktori kerja
COPY package.json ./

# Install dependensi aplikasi
RUN npm install

# Salin seluruh kode aplikasi ke direktori kerja
COPY . /app

# Tentukan port yang akan diexpose
EXPOSE 8000

# Perintah yang akan dijalankan saat container dijalankan
CMD [ "node", "server.js" ]
