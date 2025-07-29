-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Bulan Mei 2024 pada 16.35
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbresto_mila`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbdetailtransaksi`
--

CREATE TABLE `tbdetailtransaksi` (
  `iddetailtransaksi` int(20) NOT NULL,
  `idtransaksi` varchar(15) NOT NULL,
  `idmenu` varchar(6) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `totalharga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbmeja`
--

CREATE TABLE `tbmeja` (
  `idmeja` varchar(6) NOT NULL,
  `nomormeja` varchar(15) NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbmeja`
--

INSERT INTO `tbmeja` (`idmeja`, `nomormeja`, `kategori`, `status`) VALUES
('MJ0001', '1', 'Single', 'reserved'),
('MJ0002', '2', 'Double', 'reserved');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbmenu`
--

CREATE TABLE `tbmenu` (
  `idmenu` varchar(6) NOT NULL,
  `namamenu` varchar(20) NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbmenu`
--

INSERT INTO `tbmenu` (`idmenu`, `namamenu`, `kategori`, `harga`, `stok`) VALUES
('MN0001', 'Spaghetti', 'Makanan', 5000, 50),
('MN0002', 'Tahu Cabe Garam', 'Makanan', 25000, 70),
('MN0003', 'Kopi Tubruk', 'Minuman', 7000, 40),
('MN0005', 'Jus Mangga', 'Minuman', 10000, 45),
('MN0006', 'Roti Bakar', 'Makanan', 25000, 2),
('MN0008', 'SHIBAL', 'Makanan', 5000, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpegawai`
--

CREATE TABLE `tbpegawai` (
  `idpegawai` varchar(6) NOT NULL,
  `namapegawai` varchar(25) NOT NULL,
  `jk` varchar(15) NOT NULL,
  `tempatlahir` varchar(15) NOT NULL,
  `tanggallahir` date NOT NULL,
  `alamat` varchar(45) NOT NULL,
  `nohp` varchar(13) NOT NULL,
  `jenispekerjaan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpelanggan`
--

CREATE TABLE `tbpelanggan` (
  `idpelanggan` varchar(6) NOT NULL,
  `namapelanggan` varchar(25) NOT NULL,
  `jk` varchar(15) NOT NULL,
  `tempatlahir` varchar(15) NOT NULL,
  `tanggallahir` date NOT NULL,
  `alamat` varchar(45) NOT NULL,
  `nohp` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbpelanggan`
--

INSERT INTO `tbpelanggan` (`idpelanggan`, `namapelanggan`, `jk`, `tempatlahir`, `tanggallahir`, `alamat`, `nohp`) VALUES
('PL0001', 'Mila', 'Perempuan', 'Bandung ', '2019-10-03', 'Bandung', '083842'),
('PL0002', 'Kendall Jenner', 'Perempuan', 'Nganjuk', '1999-12-24', 'Cicaheum', '99998'),
('PL0003', 'Anjay', 'Laki-Laki', 'RS', '1222-11-11', 'Bojong angsa', '00987');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpengguna`
--

CREATE TABLE `tbpengguna` (
  `idpengguna` varchar(6) NOT NULL,
  `idpegawai` varchar(6) NOT NULL,
  `namapengguna` varchar(25) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `level` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbtransaksi`
--

CREATE TABLE `tbtransaksi` (
  `idtransaksi` varchar(15) NOT NULL,
  `tanggal` date NOT NULL,
  `idpengguna` varchar(6) NOT NULL,
  `idpelanggan` varchar(6) NOT NULL,
  `idmeja` varchar(6) NOT NULL,
  `hargatotal` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `bayar` int(11) NOT NULL,
  `kembali` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbdetailtransaksi`
--
ALTER TABLE `tbdetailtransaksi`
  ADD PRIMARY KEY (`iddetailtransaksi`),
  ADD KEY `idtransaksi` (`idtransaksi`,`idmenu`),
  ADD KEY `idmenu` (`idmenu`);

--
-- Indeks untuk tabel `tbmeja`
--
ALTER TABLE `tbmeja`
  ADD PRIMARY KEY (`idmeja`);

--
-- Indeks untuk tabel `tbmenu`
--
ALTER TABLE `tbmenu`
  ADD PRIMARY KEY (`idmenu`);

--
-- Indeks untuk tabel `tbpegawai`
--
ALTER TABLE `tbpegawai`
  ADD PRIMARY KEY (`idpegawai`);

--
-- Indeks untuk tabel `tbpelanggan`
--
ALTER TABLE `tbpelanggan`
  ADD PRIMARY KEY (`idpelanggan`);

--
-- Indeks untuk tabel `tbpengguna`
--
ALTER TABLE `tbpengguna`
  ADD PRIMARY KEY (`idpengguna`),
  ADD KEY `idpegawai` (`idpegawai`);

--
-- Indeks untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD PRIMARY KEY (`idtransaksi`),
  ADD KEY `idpengguna` (`idpengguna`,`idpelanggan`,`idmeja`),
  ADD KEY `idmeja` (`idmeja`),
  ADD KEY `idpelanggan` (`idpelanggan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tbdetailtransaksi`
--
ALTER TABLE `tbdetailtransaksi`
  MODIFY `iddetailtransaksi` int(20) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbdetailtransaksi`
--
ALTER TABLE `tbdetailtransaksi`
  ADD CONSTRAINT `tbdetailtransaksi_ibfk_1` FOREIGN KEY (`idmenu`) REFERENCES `tbmenu` (`idmenu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbdetailtransaksi_ibfk_2` FOREIGN KEY (`idtransaksi`) REFERENCES `tbtransaksi` (`idtransaksi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tbpengguna`
--
ALTER TABLE `tbpengguna`
  ADD CONSTRAINT `tbpengguna_ibfk_1` FOREIGN KEY (`idpegawai`) REFERENCES `tbpegawai` (`idpegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD CONSTRAINT `tbtransaksi_ibfk_1` FOREIGN KEY (`idmeja`) REFERENCES `tbmeja` (`idmeja`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbtransaksi_ibfk_2` FOREIGN KEY (`idpelanggan`) REFERENCES `tbpelanggan` (`idpelanggan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbtransaksi_ibfk_3` FOREIGN KEY (`idpengguna`) REFERENCES `tbpengguna` (`idpengguna`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
