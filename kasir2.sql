-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 30, 2020 at 03:38 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasir2`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kode_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(40) NOT NULL,
  `harga_barang` int(11) NOT NULL,
  `stok_barang` int(11) NOT NULL,
  `kode_supplier` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `harga_barang`, `stok_barang`, `kode_supplier`) VALUES
('A001', 'Ultra Milk', 5000, 199, 'S01'),
('A002', 'Ultra Mimi', 3000, 200, 'S01'),
('A003', 'The Kotak', 3500, 148, 'S01'),
('A004', 'Sari Kacang Ijo', 2500, 96, 'S01'),
('A005', 'Ultra Butter', 6000, 100, 'S01'),
('A006', 'The Bunga', 3000, 200, 'S01'),
('A007', 'Sari Asem Asli', 2500, 98, 'S01'),
('A008', 'Belfoods Royal Cheesy Bites', 20000, 150, 'S02'),
('A009', 'Belfoods Chicken Cordon Blue', 19000, 98, 'S02'),
('A010', 'Belfoods Chicken Nugget', 18000, 80, 'S02'),
('A011', 'Indomie', 3000, 398, 'S04'),
('A012', 'Sarimi', 2500, 100, 'S04'),
('A013', 'Pop Mie', 4000, 200, 'S04'),
('A014', 'Supermi', 2500, 300, 'S04'),
('A015', 'Mie  Sakura', 2000, 200, 'S04'),
('A016', 'Mie Telur Cap 3 Ayam', 7000, 400, 'S04'),
('A017', 'Pop Bihun', 6000, 500, 'S04'),
('A018', 'Cheetos', 1500, 400, 'S04'),
('A019', 'Jet-Z', 1500, 400, 'S04'),
('A020', 'Doritos', 8000, 500, 'S04'),
('A021', 'Indomilk', 4000, 100, 'S04'),
('A022', 'Milkuat', 2500, 200, 'S04'),
('A023', 'Kecap Indofood', 10000, 100, 'S04'),
('A024', 'Sambal Indofood', 10000, 100, 'S04'),
('A025', 'Bumbu Racik Indofood', 7500, 200, 'S04'),
('A026', 'Bumbu Instan Indofood', 7500, 40, 'S04'),
('A027', 'Bumbu Kaldu Indofood', 6500, 200, 'S04'),
('A028', 'Promina', 6000, 400, 'S04'),
('A029', 'GoVit', 1500, 500, 'S04'),
('A030', 'GoWell', 1500, 400, 'S04'),
('A031', 'La Fonte', 21000, 50, 'S04'),
('A032', 'Segitiga Biru', 15000, 400, 'S04'),
('A033', 'Bimoli', 12000, 200, 'S04'),
('A034', 'Palmia', 11000, 200, 'S04'),
('A035', 'Ichi Ocha', 3500, 200, 'S04'),
('A036', 'Club Mineral', 2000, 410, 'S04'),
('A037', 'Fruitamin', 1500, 140, 'S04'),
('A038', 'Royal Palmia', 13000, 420, 'S04'),
('A039', 'Lays', 2000, 125, 'S04'),
('A040', 'Chitato', 2000, 512, 'S04'),
('A041', 'Qtela', 3000, 351, 'S04'),
('A042', 'Pepsodent', 9000, 351, 'S05'),
('A043', 'Clear Shampoo', 9000, 35, 'S05'),
('A044', 'Dove Conditioner 70ml', 10000, 353, 'S05'),
('A045', 'Lifebuoy Liquid 180ml', 11000, 410, 'S05'),
('A046', 'Rexona Women 50ml', 16000, 414, 'S05'),
('A047', 'Rexona Men Roll On', 16000, 425, 'S05'),
('A048', 'Vaseline Lotion 100ml', 18000, 121, 'S05'),
('A049', 'Sunsilk Conditioner 170ml', 19000, 123, 'S05'),
('A050', 'Fair & Lovely 100g', 21000, 313, 'S05'),
('A051', 'Citra Hand And Body Lotion 400ml', 29000, 124, 'S05'),
('A052', 'Deodorant BodySpray 150ml', 36000, 412, 'S05'),
('A053', 'Treseme Shampoo 340ml', 43000, 234, 'S05'),
('A054', 'Ponds Flawless 50g', 82000, 144, 'S05'),
('A055', 'Vaseline Men Gel 100g', 99000, 144, 'S05'),
('A056', 'Promag', 4500, 241, 'S06'),
('A057', 'Kalpanax', 4000, 412, 'S06'),
('A058', 'Komix', 4000, 411, 'S06'),
('A059', 'Procold', 3500, 414, 'S06'),
('A060', 'Mixagrip', 4000, 421, 'S06'),
('A061', 'Entrostop', 4500, 121, 'S06'),
('A062', 'Fatigon', 5000, 122, 'S06'),
('A063', 'Bintang Toedjoe Panas Dalam', 2000, 412, 'S06'),
('A064', 'Bintang Toedjoe Masuk Angin', 2000, 233, 'S06'),
('A065', 'Prenagen', 6000, 312, 'S06'),
('A066', 'Morinaga', 5000, 312, 'S06'),
('A067', 'Diabetasol', 4000, 423, 'S06'),
('A068', 'Fitbar', 3500, 234, 'S06'),
('A069', 'Hydro Coco', 4000, 221, 'S06'),
('A070', 'Sakatonik Liver', 10000, 224, 'S06'),
('A071', 'Sakatonik ABC', 14000, 421, 'S06'),
('A072', 'Woods', 6000, 424, 'S06'),
('A073', 'Extra Joss', 6000, 433, 'S06'),
('A074', 'Nutrive Benecol', 5500, 244, 'S06'),
('A075', 'Vegie Fruit', 4000, 44, 'S06'),
('A076', 'Milo', 46000, 12, 'S07'),
('A077', 'Dancow', 42000, 51, 'S07'),
('A078', 'Nescafe', 14000, 533, 'S07'),
('A079', 'Koko Krunch', 18000, 233, 'S07'),
('A080', 'Carnation', 16000, 122, 'S07'),
('A081', 'Kit Kat', 7000, 124, 'S07'),
('A082', 'Cerelac', 8000, 414, 'S07'),
('A083', 'Nestea', 3000, 435, 'S07'),
('A084', 'Foxs', 10000, 123, 'S07'),
('A085', 'Corn Flakes', 14000, 424, 'S07'),
('A086', 'Malrboro Merah', 33500, 300, 'S08'),
('A087', 'Sampoerna Mild', 27000, 500, 'S08'),
('A088', 'Djarum Super Mild', 26500, 300, 'S08'),
('A089', 'Surya Profesional', 20500, 100, 'S08'),
('A090', 'Magnum Hitam', 20000, 40, 'S08'),
('A091', 'Magnum Biru', 27000, 304, 'S08'),
('A092', 'Gudang Garam Internasional Filter', 20500, 230, 'S08'),
('A093', 'Dji Sam Soe', 18000, 130, 'S08'),
('A094', 'Dunhill', 22000, 340, 'S08'),
('A095', 'Rinso', 11000, 320, 'S03'),
('A096', 'Kopi Torabika', 1500, 150, 'S09'),
('A097', 'Kopi ABC Susu', 1500, 35, 'S09'),
('A098', 'Coca Cola 100ml', 4000, 520, 'S09'),
('A099', 'Pulpy Orange 250ml', 9000, 350, 'S03'),
('A100', 'Silverqueen', 13000, 320, 'S03');

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaksi`
--

CREATE TABLE `detailtransaksi` (
  `kode_nota` varchar(10) NOT NULL,
  `nama_barang` varchar(40) NOT NULL,
  `harga_barang` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `sub_total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailtransaksi`
--

INSERT INTO `detailtransaksi` (`kode_nota`, `nama_barang`, `harga_barang`, `qty`, `sub_total`) VALUES
('UNC-001', 'Indomie', 3000, 2, 6000),
('UNC-001', 'Sari Asem Asli', 2500, 7, 17500),
('UNC-002', 'Ultra Butter', 6000, 2, 12000),
('UNC-002', 'Cheetos', 1500, 3, 4500),
('UNC-002', 'Indomie', 3000, 6, 18000),
('UNC-002', 'Sarimi', 2500, 3, 7500),
('UNC-003', 'Ultra Butter', 6000, 2, 12000),
('UNC-003', 'The Bunga', 3000, 2, 6000),
('UNC-004', 'Sari Kacang Ijo', 2500, 4, 10000),
('UNC-004', 'Sari Asem Asli', 2500, 2, 5000),
('UNC-005', 'The Kotak', 3500, 2, 7000),
('UNC-005', 'Belfoods Chicken Cordon Blue', 19000, 2, 38000),
('UNC-005', 'Indomie', 3000, 2, 6000),
('UNC-005', 'Ultra Milk', 5000, 1, 5000);

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

CREATE TABLE `pegawai` (
  `kode_pegawai` varchar(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `jenis_kelamin` varchar(3) NOT NULL,
  `bagian` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`kode_pegawai`, `nama`, `alamat`, `jenis_kelamin`, `bagian`, `password`) VALUES
('ADM04', 'Rivan Agustiawan', 'Cibuntu', 'L', 'Admin', 'rahasia'),
('SPG01', 'Rendy Ananda', 'Jalan Teluk Buyung No 21', 'L', 'kasir', '123');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `kode_supplier` varchar(10) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`kode_supplier`, `nama_supplier`, `alamat`, `no_telp`) VALUES
('S01', 'PT Ultrajaya Milk Industry', 'Bandung', '14510'),
('S02', 'PT Belfoods Indonesia', 'Cimahi', '18926'),
('S03', 'PT Niaga Harmoni', 'Jakarta', '14015'),
('S04', 'PT Indofood', 'Jakarta', '01451'),
('S05', 'PT Unilever Indonesia', 'Jakarta', '14251'),
('S06', 'PT Kalbe Farma', 'Jakarta', '25252'),
('S07', 'PT Nestle Indonesia', 'Karawang', '42231'),
('S08', 'PT Djarum', 'Kudus', '35212'),
('S09', 'PT Tara Kusuma Indah', 'Jakarta Barat', '12314');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `kode_nota` varchar(10) NOT NULL,
  `tgl_transaksi` date NOT NULL,
  `kode_pegawai` varchar(12) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`kode_nota`, `tgl_transaksi`, `kode_pegawai`, `total_harga`) VALUES
('UNC-001', '2020-01-30', '', 23500),
('UNC-002', '2020-01-30', 'ADM04', 42000),
('UNC-003', '2020-01-30', 'ADM04', 18000),
('UNC-004', '2020-01-30', 'ADM04', 15000),
('UNC-005', '2020-01-30', 'SPG01', 56000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`kode_pegawai`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`kode_supplier`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
