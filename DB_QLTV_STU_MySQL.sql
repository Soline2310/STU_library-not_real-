-- ============================================================
-- QuanLyThuVienSTU - MySQL Compatible Version
-- Converted from SQL Server (T-SQL) to MySQL
-- ============================================================

CREATE DATABASE IF NOT EXISTS QuanLyThuVienSTU
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE QuanLyThuVienSTU;

-- ============================================================
-- TABLES
-- ============================================================

CREATE TABLE TAC_GIA (
    MaTacGia VARCHAR(20) PRIMARY KEY,
    TenTacGia VARCHAR(100) NOT NULL,
    NamSinh INT,
    QuocTich VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE NHA_XUAT_BAN (
    MaNXB VARCHAR(20) PRIMARY KEY,
    TenNXB VARCHAR(100) NOT NULL,
    DiaChi VARCHAR(200),
    SDT VARCHAR(15)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE SACH (
    MaSach VARCHAR(20) PRIMARY KEY,
    TenSach VARCHAR(200) NOT NULL,
    MaTacGia VARCHAR(20),
    TheLoai VARCHAR(100),
    MaNXB VARCHAR(20),
    SoLuong INT DEFAULT 0,
    CONSTRAINT FK_SACH_TAC_GIA FOREIGN KEY (MaTacGia) REFERENCES TAC_GIA(MaTacGia) ON DELETE SET NULL,
    CONSTRAINT FK_SACH_NXB FOREIGN KEY (MaNXB) REFERENCES NHA_XUAT_BAN(MaNXB) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE DOC_GIA (
    MaDocGia VARCHAR(20) PRIMARY KEY,
    TenDocGia VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    SDT VARCHAR(15),
    Email VARCHAR(100),
    DiaChi VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE THU_THU (
    MaThuThu VARCHAR(20) PRIMARY KEY,
    TenThuThu VARCHAR(100) NOT NULL,
    CaTruc VARCHAR(50),
    SDT VARCHAR(15),
    TaiKhoan VARCHAR(50),
    MatKhau VARCHAR(50),
    VaiTro VARCHAR(50) DEFAULT 'Thu thu'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE PHIEU_MUON (
    MaPhieu VARCHAR(20) PRIMARY KEY,
    MaDocGia VARCHAR(20),
    MaSach VARCHAR(20),
    NgayMuon DATE,
    HanTra DATE,
    TrangThai VARCHAR(50) DEFAULT 'Dang muon',
    CONSTRAINT FK_PM_DOC_GIA FOREIGN KEY (MaDocGia) REFERENCES DOC_GIA(MaDocGia) ON DELETE CASCADE,
    CONSTRAINT FK_PM_SACH FOREIGN KEY (MaSach) REFERENCES SACH(MaSach) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE PHIEU_NHAP (
    MaPhieuNhap VARCHAR(20) PRIMARY KEY,
    MaSach VARCHAR(20),
    SoLuong INT,
    NhaCungCap VARCHAR(150),
    NgayNhap DATE,
    TinhTrang VARCHAR(50) DEFAULT 'Da nhan',
    CONSTRAINT FK_PN_SACH FOREIGN KEY (MaSach) REFERENCES SACH(MaSach) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- IDENTITY(1,1) -> AUTO_INCREMENT | NVARCHAR(MAX) -> TEXT
CREATE TABLE CAI_DAT_CHUNG (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    TenThuVien VARCHAR(150),
    DiaChi VARCHAR(250),
    NoiQuy TEXT,
    MaThue VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- DATA  (N'...' unicode prefix removed; utf8mb4 handles it)
-- ============================================================

INSERT INTO TAC_GIA VALUES 
('TG01', 'Nguyễn Nhật Ánh', 1955, 'Việt Nam'),
('TG02', 'Vũ Trọng Phụng', 1912, 'Việt Nam'),
('TG03', 'J.K. Rowling', 1965, 'Anh'),
('TG04', 'Tô Hoài', 1920, 'Việt Nam'),
('TG05', 'Robert C. Martin', 1952, 'Mỹ'),
('TG06', 'Nam Cao', 1915, 'Việt Nam'),
('TG07', 'Xuân Diệu', 1916, 'Việt Nam'),
('TG08', 'Haruki Murakami', 1949, 'Nhật Bản'),
('TG09', 'Higashino Keigo', 1958, 'Nhật Bản'),
('TG10', 'Dale Carnegie', 1888, 'Mỹ'),
('TG11', 'Stephen Hawking', 1942, 'Anh'),
('TG12', 'Paulo Coelho', 1947, 'Brazil'),
('TG13', 'Dan Brown', 1964, 'Mỹ'),
('TG14', 'Ngô Tất Tố', 1894, 'Việt Nam'),
('TG15', 'Thạch Lam', 1910, 'Việt Nam');

INSERT INTO NHA_XUAT_BAN VALUES 
('NXB01', 'NXB Trẻ', 'Quận 3, TP.HCM', '02811112222'),
('NXB02', 'NXB Kim Đồng', 'Quận 1, TP.HCM', '02833334444'),
('NXB03', 'NXB Giáo Dục', 'Quận 5, TP.HCM', '02855556666'),
('NXB04', 'Nhã Nam', 'Phú Nhuận, TP.HCM', '02877778888'),
('NXB05', 'NXB Khoa Học Kỹ Thuật', 'Đống Đa, Hà Nội', '02499990000'),
('NXB06', 'NXB Văn Học', 'Hà Nội', '02411122233'),
('NXB07', 'NXB Tổng hợp TP.HCM', 'Quận 1, TP.HCM', '02822233344'),
('NXB08', 'NXB Thanh Niên', 'Hà Nội', '02433344455'),
('NXB09', 'NXB Phụ Nữ', 'Hà Nội', '02444455566'),
('NXB10', 'NXB Lao Động', 'Đống Đa, Hà Nội', '02455566677');

INSERT INTO SACH VALUES 
('S001', 'Mắt Biếc', 'TG01', 'Tiểu thuyết', 'NXB01', 20),
('S002', 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'TG01', 'Truyện dài', 'NXB01', 15),
('S003', 'Số Đỏ', 'TG02', 'Tiểu thuyết', 'NXB04', 10),
('S004', 'Harry Potter và Hòn Đá Phù Thủy', 'TG03', 'Viễn tưởng', 'NXB01', 30),
('S005', 'Harry Potter và Phòng Chứa Bí Mật', 'TG03', 'Viễn tưởng', 'NXB01', 25),
('S006', 'Dế Mèn Phiêu Lưu Ký', 'TG04', 'Thiếu nhi', 'NXB02', 40),
('S007', 'Clean Code', 'TG05', 'Công nghệ thông tin', 'NXB05', 10),
('S008', 'Lập Trình Java Cơ Bản', 'TG05', 'Công nghệ thông tin', 'NXB05', 12),
('S009', 'Cấu Trúc Dữ Liệu Và Giải Thuật', NULL, 'Giáo trình', 'NXB03', 50),
('S010', 'Cơ Sở Dữ Liệu SQL', NULL, 'Giáo trình', 'NXB03', 45),
('S011', 'Cô Gái Đến Từ Hôm Qua', 'TG01', 'Truyện dài', 'NXB01', 18),
('S012', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'TG01', 'Truyện dài', 'NXB01', 22),
('S013', 'Giông Tố', 'TG02', 'Tiểu thuyết', 'NXB04', 8),
('S014', 'Harry Potter và Tù Nhân Azkaban', 'TG03', 'Viễn tưởng', 'NXB01', 20),
('S015', 'Lập Trình Hướng Đối Tượng', 'TG05', 'Công nghệ thông tin', 'NXB05', 15),
('S016', 'Chí Phèo', 'TG06', 'Truyện ngắn', 'NXB06', 30),
('S017', 'Sống Mòn', 'TG06', 'Tiểu thuyết', 'NXB06', 15),
('S018', 'Rừng Na Uy', 'TG08', 'Tiểu thuyết', 'NXB08', 25),
('S019', 'Phía Nam Biên Giới, Phía Tây Mặt Trời', 'TG08', 'Tiểu thuyết', 'NXB08', 10),
('S020', 'Bạch Dạ Hành', 'TG09', 'Trinh thám', 'NXB04', 40),
('S021', 'Phía Sau Nghi Can X', 'TG09', 'Trinh thám', 'NXB04', 35),
('S022', 'Đắc Nhân Tâm', 'TG10', 'Kỹ năng sống', 'NXB07', 100),
('S023', 'Quẳng Gánh Lo Đi Và Vui Sống', 'TG10', 'Kỹ năng sống', 'NXB07', 80),
('S024', 'Lược Sử Thời Gian', 'TG11', 'Khoa học', 'NXB01', 20),
('S025', 'Nhà Giả Kim', 'TG12', 'Tiểu thuyết', 'NXB06', 50),
('S026', 'Mật Mã Da Vinci', 'TG13', 'Trinh thám', 'NXB08', 30),
('S027', 'Thiên Thần Và Ác Quỷ', 'TG13', 'Trinh thám', 'NXB08', 25),
('S028', 'Tắt Đèn', 'TG14', 'Tiểu thuyết', 'NXB06', 45),
('S029', 'Việc Làng', 'TG14', 'Phóng sự', 'NXB06', 15),
('S030', 'Gió Lạnh Đầu Mùa', 'TG15', 'Truyện ngắn', 'NXB02', 35),
('S031', 'Hai Đứa Trẻ', 'TG15', 'Truyện ngắn', 'NXB02', 40),
('S032', 'Hà Nội Băm Sáu Phố Phường', 'TG15', 'Tùy bút', 'NXB06', 20),
('S033', 'Nhật Ký Trong Tù', NULL, 'Thơ', 'NXB06', 60),
('S034', 'Truyện Kiều', NULL, 'Thơ', 'NXB06', 100),
('S035', 'Lục Vân Tiên', NULL, 'Thơ', 'NXB06', 55),
('S036', 'Lập trình Python từ Zero', NULL, 'Công nghệ thông tin', 'NXB05', 30),
('S037', 'Machine Learning Cơ Bản', NULL, 'Công nghệ thông tin', 'NXB05', 15),
('S038', 'Nhập môn Trí tuệ nhân tạo', NULL, 'Giáo trình', 'NXB03', 40),
('S039', 'Kinh tế vĩ mô', NULL, 'Giáo trình', 'NXB03', 80),
('S040', 'Kinh tế vi mô', NULL, 'Giáo trình', 'NXB03', 75),
('S041', 'Kế toán tài chính', NULL, 'Giáo trình', 'NXB03', 60),
('S042', 'Tâm lý học tội phạm', NULL, 'Tâm lý học', 'NXB09', 25),
('S043', 'Nghệ thuật giao tiếp để thành công', NULL, 'Kỹ năng sống', 'NXB09', 45),
('S044', 'Đọc vị bất kỳ ai', NULL, 'Tâm lý học', 'NXB10', 35),
('S045', 'Sức mạnh của thói quen', NULL, 'Kỹ năng sống', 'NXB10', 40);

INSERT INTO DOC_GIA VALUES 
('DG001', 'Phạm Trung Kiên', '2004-05-12', '0901234567', 'kien@stu.edu.vn', 'Quận 8, TP.HCM'),
('DG002', 'Trần Thị Mai', '2005-11-20', '0912345678', 'mai@stu.edu.vn', 'Bình Chánh, TP.HCM'),
('DG003', 'Lê Hoàng Long', '2003-02-15', '0923456789', 'long@stu.edu.vn', 'Quận 7, TP.HCM'),
('DG004', 'Nguyễn Bảo Ngọc', '2004-08-08', '0934567890', 'ngoc@stu.edu.vn', 'Quận 5, TP.HCM'),
('DG005', 'Vũ Đức Mạnh', '2002-12-30', '0945678901', 'manh@stu.edu.vn', 'Quận 1, TP.HCM'),
('DG006', 'Hoàng Thanh Trúc', '2005-04-25', '0956789012', 'truc@stu.edu.vn', 'Quận 4, TP.HCM'),
('DG007', 'Đinh Quang Hải', '2003-09-10', '0967890123', 'hai@stu.edu.vn', 'Quận 10, TP.HCM'),
('DG008', 'Bùi Thu Thủy', '2004-01-05', '0978901234', 'thuy@stu.edu.vn', 'Tân Phú, TP.HCM'),
('DG009', 'Đỗ Minh Tuấn', '2002-07-18', '0989012345', 'tuan@stu.edu.vn', 'Tân Bình, TP.HCM'),
('DG010', 'Phan Cẩm Ly', '2005-03-22', '0990123456', 'ly@stu.edu.vn', 'Gò Vấp, TP.HCM'),
('DG011', 'Trần Tuấn Kiệt', '2004-02-14', '0981112222', 'kiet@stu.edu.vn', 'Quận 8, TP.HCM'),
('DG012', 'Lê Quốc Bảo', '2003-05-19', '0982223333', 'bao@stu.edu.vn', 'Quận 10, TP.HCM'),
('DG013', 'Nguyễn Tấn Phát', '2005-08-25', '0983334444', 'phat@stu.edu.vn', 'Quận 7, TP.HCM'),
('DG014', 'Phạm Mỹ Duyên', '2004-11-03', '0984445555', 'duyen@stu.edu.vn', 'Bình Chánh, TP.HCM'),
('DG015', 'Đinh Tuấn Vũ', '2002-09-12', '0985556666', 'vu@stu.edu.vn', 'Quận 4, TP.HCM'),
('DG016', 'Bùi Ánh Tuyết', '2005-12-01', '0986667777', 'tuyet@stu.edu.vn', 'Gò Vấp, TP.HCM'),
('DG017', 'Hoàng Vĩnh Phát', '2003-01-18', '0987778888', 'phat.hv@stu.edu.vn', 'Tân Phú, TP.HCM'),
('DG018', 'Ngô Gia Huy', '2004-06-20', '0988889999', 'huy@stu.edu.vn', 'Tân Bình, TP.HCM'),
('DG019', 'Võ Phương Thảo', '2005-03-08', '0989990000', 'thao@stu.edu.vn', 'Quận 1, TP.HCM'),
('DG020', 'Lý Huỳnh Khang', '2002-10-15', '0990001111', 'khang@stu.edu.vn', 'Quận 3, TP.HCM'),
('DG021', 'Hồ Hữu Nhân', '2003-04-22', '0991112222', 'nhan@stu.edu.vn', 'Quận 5, TP.HCM'),
('DG022', 'Trịnh Xuân Bách', '2004-07-09', '0992223333', 'bach@stu.edu.vn', 'Quận 8, TP.HCM'),
('DG023', 'Đào Hải Yến', '2005-09-05', '0993334444', 'yen@stu.edu.vn', 'Quận 10, TP.HCM'),
('DG024', 'Châu Ngọc Hân', '2002-11-28', '0994445555', 'han@stu.edu.vn', 'Bình Tân, TP.HCM'),
('DG025', 'Lương Tuấn Phong', '2003-02-11', '0995556666', 'phong@stu.edu.vn', 'Bình Chánh, TP.HCM'),
('DG026', 'Mai Hữu Nghĩa', '2004-08-16', '0996667777', 'nghia@stu.edu.vn', 'Quận 7, TP.HCM'),
('DG027', 'Tống Mỹ Linh', '2005-01-25', '0997778888', 'linh@stu.edu.vn', 'Quận 4, TP.HCM'),
('DG028', 'Đoàn Bảo Lâm', '2002-05-30', '0998889999', 'lam@stu.edu.vn', 'Nhà Bè, TP.HCM'),
('DG029', 'Dương Tường Vy', '2003-12-19', '0999990000', 'vy@stu.edu.vn', 'Quận 8, TP.HCM'),
('DG030', 'Phùng Thanh Xuân', '2004-03-07', '0900001111', 'xuan@stu.edu.vn', 'Quận 1, TP.HCM'),
('DG031', 'Vương Nhật Minh', '2005-10-21', '0901112222', 'minh@stu.edu.vn', 'Gò Vấp, TP.HCM'),
('DG032', 'Thái Cẩm Tú', '2002-06-14', '0902223333', 'tu@stu.edu.vn', 'Tân Phú, TP.HCM'),
('DG033', 'Lại Diễm My', '2003-08-02', '0903334444', 'my@stu.edu.vn', 'Tân Bình, TP.HCM'),
('DG034', 'Mạch Thế Vinh', '2004-04-26', '0904445555', 'vinh@stu.edu.vn', 'Quận 10, TP.HCM'),
('DG035', 'Hàn Thiên Tôn', '2005-07-12', '0905556666', 'ton@stu.edu.vn', 'Quận 5, TP.HCM'),
('DG036', 'Chung Khắc Tiệp', '2002-01-09', '0906667777', 'tiep@stu.edu.vn', 'Quận 3, TP.HCM'),
('DG037', 'Tiết Trọng Đạt', '2003-11-23', '0907778888', 'dat@stu.edu.vn', 'Quận 8, TP.HCM'),
('DG038', 'Nghiêm Xuân Quyết', '2004-09-17', '0908889999', 'quyet@stu.edu.vn', 'Quận 7, TP.HCM'),
('DG039', 'Giang Thị Nụ', '2005-02-04', '0909990000', 'nu@stu.edu.vn', 'Bình Chánh, TP.HCM'),
('DG040', 'Cấn Phương Nam', '2002-08-31', '0910001111', 'nam@stu.edu.vn', 'Quận 4, TP.HCM');

INSERT INTO THU_THU VALUES 
('ADMIN', 'Quản Trị Viên', 'Hành chính', '0999999999', 'admin', 'admin123', 'Admin'),
('TT01', 'Nguyễn Thị Thu Nga', 'Sáng', '0909999888', 'TT01', '123456', 'Thủ thư'),
('TT02', 'Trần Văn Bình', 'Chiều', '0918888777', 'TT02', '123456', 'Thủ thư'),
('TT03', 'Lê Thị Lan Anh', 'Tối', '0927777666', 'TT03', '123456', 'Thủ thư'),
('TT04', 'Phạm Nhật Vượng', 'Sáng', '0936666555', 'TT04', '123456', 'Thủ thư'),
('TT05', 'Lê Minh Trí', 'Chiều', '0941112222', 'TT05', '123456', 'Thủ thư'),
('TT06', 'Phan Hồng Hạnh', 'Tối', '0942223333', 'TT06', '123456', 'Thủ thư'),
('TT07', 'Võ Tấn Lộc', 'Sáng', '0943334444', 'TT07', '123456', 'Thủ thư'),
('TT08', 'Ngô Thanh Vân', 'Chiều', '0944445555', 'TT08', '123456', 'Thủ thư'),
('TT09', 'Bùi Bích Phương', 'Tối', '0945556666', 'TT09', '123456', 'Thủ thư');

INSERT INTO PHIEU_MUON VALUES 
('PM001', 'DG001', 'S007', '2026-05-15', '2026-05-29', 'Đang mượn'),
('PM002', 'DG002', 'S001', '2026-05-18', '2026-06-01', 'Đang mượn'),
('PM003', 'DG003', 'S004', '2026-05-10', '2026-05-24', 'Quá hạn'),
('PM004', 'DG004', 'S010', '2026-05-01', '2026-05-15', 'Đã trả'),
('PM005', 'DG005', 'S008', '2026-05-20', '2026-06-03', 'Đang mượn'),
('PM006', 'DG001', 'S002', '2026-05-22', '2026-06-05', 'Đang mượn'),
('PM007', 'DG011', 'S025', '2026-05-05', '2026-05-19', 'Quá hạn'),
('PM008', 'DG015', 'S018', '2026-05-12', '2026-05-26', 'Đang mượn'),
('PM009', 'DG020', 'S038', '2026-04-20', '2026-05-04', 'Đã trả'),
('PM010', 'DG025', 'S022', '2026-05-18', '2026-06-01', 'Đang mượn'),
('PM011', 'DG030', 'S030', '2026-05-21', '2026-06-04', 'Đang mượn'),
('PM012', 'DG035', 'S040', '2026-05-15', '2026-05-29', 'Đang mượn'),
('PM013', 'DG012', 'S016', '2026-05-10', '2026-05-24', 'Đang mượn'),
('PM014', 'DG018', 'S020', '2026-05-01', '2026-05-15', 'Đã trả'),
('PM015', 'DG022', 'S042', '2026-05-22', '2026-06-05', 'Đang mượn'),
('PM016', 'DG028', 'S026', '2026-05-14', '2026-05-28', 'Đang mượn'),
('PM017', 'DG033', 'S034', '2026-04-25', '2026-05-09', 'Đã trả'),
('PM018', 'DG038', 'S036', '2026-05-23', '2026-06-06', 'Đang mượn'),
('PM019', 'DG014', 'S024', '2026-05-19', '2026-06-02', 'Đang mượn'),
('PM020', 'DG019', 'S028', '2026-05-08', '2026-05-22', 'Quá hạn'),
('PM021', 'DG029', 'S044', '2026-05-17', '2026-05-31', 'Đang mượn');

INSERT INTO PHIEU_NHAP VALUES 
('PN001', 'S001', 50, 'Công ty phát hành sách Fahasa', '2026-01-10', 'Đã nhận'),
('PN002', 'S007', 20, 'Nhà sách Phương Nam', '2026-02-15', 'Đã nhận'),
('PN003', 'S004', 100, 'Tiki Trading', '2026-04-20', 'Đã nhận'),
('PN004', 'S009', 30, 'Công ty phát hành sách Fahasa', '2026-05-23', 'Đang giao'),
('PN005', 'S022', 50, 'Tiki Trading', '2026-05-05', 'Đã nhận'),
('PN006', 'S034', 80, 'Công ty phát hành sách Fahasa', '2026-05-12', 'Đã nhận'),
('PN007', 'S025', 40, 'Nhà sách Phương Nam', '2026-05-18', 'Đã nhận'),
('PN008', 'S038', 20, 'NXB Giáo Dục', '2026-05-20', 'Đang giao'),
('PN009', 'S044', 30, 'Tiki Trading', '2026-05-22', 'Đang giao');

INSERT INTO CAI_DAT_CHUNG (TenThuVien, DiaChi, NoiQuy, MaThue) VALUES 
('Thư Viện Trung Tâm STU', '180 Cao Lỗ, Phường 4, Quận 8, TP.HCM', '1. Xuất trình thẻ sinh viên. 2. Giữ trật tự. 3. Không mang đồ ăn, thức uống.', '03123456789');


-- ============================================================
-- TRIGGERS
-- SQL Server uses inserted/deleted virtual tables;
-- MySQL uses NEW and OLD row references per FOR EACH ROW
-- ============================================================

DELIMITER $$

-- Trigger trừ sách khi mượn
CREATE TRIGGER trg_MuonSach
AFTER INSERT ON PHIEU_MUON
FOR EACH ROW
BEGIN
    DECLARE v_SoLuong INT;
    SELECT SoLuong INTO v_SoLuong FROM SACH WHERE MaSach = NEW.MaSach;
    IF v_SoLuong <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Loi: Sach nay da het!';
    END IF;
    IF NEW.TrangThai = 'Đang mượn' THEN
        UPDATE SACH SET SoLuong = SoLuong - 1 WHERE MaSach = NEW.MaSach;
    END IF;
END$$

-- Trigger cộng sách khi trả
-- IF UPDATE(col) in SQL Server -> compare OLD vs NEW in MySQL
CREATE TRIGGER trg_TraSach
AFTER UPDATE ON PHIEU_MUON
FOR EACH ROW
BEGIN
    IF NEW.TrangThai != OLD.TrangThai THEN
        IF NEW.TrangThai = 'Đã trả' AND OLD.TrangThai IN ('Đang mượn', 'Quá hạn') THEN
            UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = NEW.MaSach;
        END IF;
    END IF;
END$$

-- Trigger phục hồi sách khi xóa phiếu mượn sai
CREATE TRIGGER trg_XoaPhieuMuon
AFTER DELETE ON PHIEU_MUON
FOR EACH ROW
BEGIN
    IF OLD.TrangThai IN ('Đang mượn', 'Quá hạn') THEN
        UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = OLD.MaSach;
    END IF;
END$$

-- MySQL cannot combine AFTER INSERT, UPDATE in one trigger;
-- must create two separate triggers
CREATE TRIGGER trg_NhapKho_Insert
AFTER INSERT ON PHIEU_NHAP
FOR EACH ROW
BEGIN
    IF NEW.TinhTrang = 'Đã nhận' THEN
        UPDATE SACH SET SoLuong = SoLuong + NEW.SoLuong WHERE MaSach = NEW.MaSach;
    END IF;
END$$

CREATE TRIGGER trg_NhapKho_Update
AFTER UPDATE ON PHIEU_NHAP
FOR EACH ROW
BEGIN
    IF NEW.TinhTrang = 'Đã nhận' AND OLD.TinhTrang != 'Đã nhận' THEN
        UPDATE SACH SET SoLuong = SoLuong + NEW.SoLuong WHERE MaSach = NEW.MaSach;
    END IF;
END$$

DELIMITER ;


-- ============================================================
-- FUNCTIONS
-- SQL Server: @var DECLARE with = init, GETDATE(), DATEDIFF(DAY,a,b)
-- MySQL:      DECLARE v_var TYPE DEFAULT val, CURDATE(), DATEDIFF(b,a)
--             Must declare READS SQL DATA (not DETERMINISTIC)
-- ============================================================

DELIMITER $$

CREATE FUNCTION fn_SoSachDangMuon(p_MaDocGia VARCHAR(20))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_SoLuong INT DEFAULT 0;
    SELECT COUNT(MaPhieu) INTO v_SoLuong
    FROM PHIEU_MUON
    WHERE MaDocGia = p_MaDocGia AND TrangThai IN ('Đang mượn', 'Quá hạn');
    RETURN v_SoLuong;
END$$

-- GETDATE() -> CURDATE() | DATEDIFF(DAY, a, b) -> DATEDIFF(b, a)
CREATE FUNCTION fn_NgayTreHan(p_MaPhieu VARCHAR(20))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_NgayTre INT DEFAULT 0;
    DECLARE v_HanTra DATE;
    SELECT HanTra INTO v_HanTra
    FROM PHIEU_MUON
    WHERE MaPhieu = p_MaPhieu AND TrangThai = 'Quá hạn';
    IF v_HanTra IS NOT NULL AND v_HanTra < CURDATE() THEN
        SET v_NgayTre = DATEDIFF(CURDATE(), v_HanTra);
    END IF;
    RETURN v_NgayTre;
END$$

CREATE FUNCTION fn_TonKho(p_MaSach VARCHAR(20))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_TonKho INT DEFAULT 0;
    SELECT SoLuong INTO v_TonKho FROM SACH WHERE MaSach = p_MaSach;
    RETURN v_TonKho;
END$$

DELIMITER ;


-- ============================================================
-- STORED PROCEDURES
-- SQL Server: @param -> MySQL: IN p_param
-- RAISERROR  -> SIGNAL SQLSTATE '45000'
-- dbo.fn_... -> fn_... (no schema prefix in MySQL)
-- ============================================================

DELIMITER $$

CREATE PROCEDURE sp_LapPhieuMuon(
    IN p_MaPhieu   VARCHAR(20),
    IN p_MaDocGia  VARCHAR(20),
    IN p_MaSach    VARCHAR(20),
    IN p_NgayMuon  DATE,
    IN p_HanTra    DATE
)
BEGIN
    IF fn_SoSachDangMuon(p_MaDocGia) >= 3 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Tu choi: Doc gia dang giu 3 cuon chua tra!';
    END IF;
    INSERT INTO PHIEU_MUON (MaPhieu, MaDocGia, MaSach, NgayMuon, HanTra, TrangThai)
    VALUES (p_MaPhieu, p_MaDocGia, p_MaSach, p_NgayMuon, p_HanTra, 'Đang mượn');
END$$

CREATE PROCEDURE sp_TraSach(
    IN p_MaPhieu VARCHAR(20)
)
BEGIN
    UPDATE PHIEU_MUON
    SET TrangThai = 'Đã trả'
    WHERE MaPhieu = p_MaPhieu AND TrangThai IN ('Đang mượn', 'Quá hạn');
END$$

CREATE PROCEDURE sp_ThongKeSachMuon(
    IN p_TuNgay DATE,
    IN p_DenNgay DATE
)
BEGIN
    SELECT s.MaSach, s.TenSach, COUNT(pm.MaPhieu) AS TongLuotMuon
    FROM PHIEU_MUON pm
    JOIN SACH s ON pm.MaSach = s.MaSach
    WHERE pm.NgayMuon >= p_TuNgay AND pm.NgayMuon <= p_DenNgay
    GROUP BY s.MaSach, s.TenSach
    ORDER BY TongLuotMuon DESC;
END$$

CREATE PROCEDURE sp_ThongKeDocGia(
    IN p_TuNgay DATE,
    IN p_DenNgay DATE
)
BEGIN
    SELECT dg.MaDocGia, dg.TenDocGia, COUNT(pm.MaPhieu) AS SoSachDaMuon
    FROM PHIEU_MUON pm
    JOIN DOC_GIA dg ON pm.MaDocGia = dg.MaDocGia
    WHERE pm.NgayMuon >= p_TuNgay AND pm.NgayMuon <= p_DenNgay
    GROUP BY dg.MaDocGia, dg.TenDocGia
    ORDER BY SoSachDaMuon DESC;
END$$

DELIMITER ;
