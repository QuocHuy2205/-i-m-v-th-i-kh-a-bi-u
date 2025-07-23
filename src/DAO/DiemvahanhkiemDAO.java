package DAO;

import DBConnect.Connect;
import Model.Diem;
import Model.HocSinh;
import Model.Lop;
import Model.MonHoc;
import Model.HocKy;
import Model.NamHoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiemvahanhkiemDAO {
   public List<MonHoc> getAllMonHoc() {
        List<MonHoc> monHocList = new ArrayList<>();
        String sql = "SELECT * FROM MonHoc";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MonHoc monHoc = new MonHoc();
                monHoc.setMaMonHoc(rs.getString("MaMonHoc"));
                monHoc.setTenMonHoc(rs.getString("TenMonHoc"));
                monHoc.setSoTinChi(rs.getInt("SoTiet"));
                monHoc.setKhoi(rs.getString("khoi"));
                monHoc.setMaHocKy(rs.getString("MaHocKy"));
                monHocList.add(monHoc);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
        return monHocList;
    }

    public List<Lop> getAllLop() {
        List<Lop> lopList = new ArrayList<>();
        String sql = "SELECT * FROM LopHoc";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lop lop = new Lop();
                lop.setMaLop(rs.getString("MaLop"));
                lop.setTenLop(rs.getString("TenLop"));
                lop.setMaPhongHoc(rs.getString("Ma_Phong_hoc"));
                lop.setKhoi(rs.getInt("Khoi"));
                lop.setNamHoc(rs.getString("NienKhoa"));
                lop.setSiSo(rs.getInt("SiSo"));
                lop.setMaNguoiDung(rs.getString("Ma_nguoi_dung"));
                lopList.add(lop);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
        return lopList;
    }

    public List<NamHoc> getAllNamHoc() {
        List<NamHoc> namHocList = new ArrayList<>();
        String sql = "SELECT MoTa, MaHocKy FROM NamHoc";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NamHoc namHoc = new NamHoc(rs.getString("MoTa"), rs.getString("MaHocKy"));
                namHocList.add(namHoc);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách năm học: " + e.getMessage());
        }
        return namHocList;
    }

    public List<HocKy> getAllHocKy() {
    List<HocKy> hocKyList = new ArrayList<>();
    String sql = "SELECT MaHocKy, TenHocKy FROM HocKy";
    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            HocKy hocKy = new HocKy();
            hocKy.setMaHocKy(rs.getString("MaHocKy"));
            hocKy.setTenHocKy(rs.getString("TenHocKy"));
            hocKyList.add(hocKy);
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách học kỳ: " + e.getMessage());
        e.printStackTrace();
    }
    return hocKyList;
}


    public List<Diem> getAllDiem() {
        List<Diem> diemList = new ArrayList<>();
        String sql = "SELECT d.Ma, d.MaHocSinh, hs.HoTen, d.MaMonHoc, mh.TenMonHoc, d.diem45Phut, d.diemGiuaKy, d.diemCuoiKy, " +
                     "d.diemTrungBinh, d.hocLuc, hk.Loai AS hanhKiem, d.MaHocKy, hky.TenHocKy, nh.MoTa AS namHoc " +
                     "FROM DiemSo d " +
                     "JOIN HocSinh hs ON d.MaHocSinh = hs.MaHocSinh " +
                     "JOIN MonHoc mh ON d.MaMonHoc = mh.MaMonHoc " +
                     "LEFT JOIN HanhKiem hk ON d.MaHocSinh = hk.MaHocSinh AND d.MaHocKy = hk.MaHocKy " +
                     "JOIN HocKy hky ON d.MaHocKy = hky.MaHocKy " +
                     "JOIN NamHoc nh ON d.MoTa = nh.MoTa";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Diem diem = new Diem();
                diem.setMa(rs.getString("Ma"));
                diem.setMaHocSinh(rs.getString("MaHocSinh"));
                diem.setMaMonHoc(rs.getString("MaMonHoc"));
                diem.setDiem45Phut(rs.getDouble("diem45Phut"));
                diem.setDiemGiuaKy(rs.getDouble("diemGiuaKy"));
                diem.setDiemCuoiKy(rs.getDouble("diemCuoiKy"));
                diem.setDiemTrungBinh(rs.getDouble("diemTrungBinh"));
                diem.setHocLuc(rs.getString("hocLuc"));
                diem.setMaHocKy(rs.getString("MaHocKy") != null ? rs.getString("MaHocKy") : "");
                diem.setMoTa(rs.getString("namHoc"));
                diemList.add(diem);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách điểm: " + e.getMessage());
        }
        return diemList;
    }

   public List<Object[]> getLopData(String maLop, String moTaNamHoc) {
        List<Object[]> dataList = new ArrayList<>();
        String sql = "SELECT hs.HoTen, hs.MaHocSinh, AVG(d.diemTrungBinh) AS diemTBTong, " +
                     "CASE " +
                     "    WHEN AVG(d.diemTrungBinh) >= 8.0 THEN 'Giỏi' " +
                     "    WHEN AVG(d.diemTrungBinh) >= 6.5 THEN 'Khá' " +
                     "    WHEN AVG(d.diemTrungBinh) >= 5.0 THEN 'Trung bình' " +
                     "    ELSE 'Yếu' " +
                     "END AS hocLuc, hk.Loai AS hanhKiem, hk.GhiChu " +
                     "FROM HocSinh hs " +
                     "LEFT JOIN DiemSo d ON hs.MaHocSinh = d.MaHocSinh AND d.MoTa = ? " +
                     "LEFT JOIN HanhKiem hk ON hs.MaHocSinh = hk.MaHocSinh AND d.MaHocKy = hk.MaHocKy " +
                     "WHERE hs.MaLop = ? " +
                     "GROUP BY hs.MaHocSinh, hs.HoTen, hk.Loai, hk.GhiChu";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, moTaNamHoc);
            ps.setString(2, maLop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString("HoTen");
                row[1] = rs.getString("MaHocSinh");
                row[2] = rs.getDouble("diemTBTong") != 0 ? String.format("%.2f", rs.getDouble("diemTBTong")) : "0.00";
                row[3] = rs.getString("hocLuc");
                row[4] = rs.getString("hanhKiem") != null ? rs.getString("hanhKiem") : "N/A";
                row[5] = rs.getString("GhiChu") != null ? rs.getString("GhiChu") : "";
                dataList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy dữ liệu lớp: " + e.getMessage());
        }
        return dataList;
    }

    public boolean insertDiem(String ma, String maHocSinh, String maMonHoc,
                         float diem45Phut, float diemGiuaKy, float diemCuoiKy, float diemTrungBinh,
                         String hocLuc, String moTaNamHoc, String maHocKy) {
        String sql = "INSERT INTO DiemSo (Ma, MaHocSinh, MaMonHoc, diem45Phut, diemGiuaKy, diemCuoiKy, diemTrungBinh, hocLuc, MoTa, MaHocKy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ps.setString(2, maHocSinh);
            ps.setString(3, maMonHoc);
            ps.setFloat(4, diem45Phut);
            ps.setFloat(5, diemGiuaKy);
            ps.setFloat(6, diemCuoiKy);
            ps.setFloat(7, diemTrungBinh);
            ps.setString(8, hocLuc);
            ps.setString(9, moTaNamHoc);
            ps.setString(10, maHocKy);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi chèn điểm: " + e.getMessage());
            return false;
        }
    }

    public boolean isHocSinhExists(String maHocSinh) {
        String sql = "SELECT COUNT(*) FROM HocSinh WHERE MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra học sinh: " + e.getMessage());
        }
        return false;
    }

    public boolean insertHanhKiem(String maHocSinh, String hanhKiem, String ghiChu, String maHocKy) {
        String maHanhKiem = generateHanhKiemCode();
        String sql = "INSERT INTO HanhKiem (MaHK, MaHocSinh, Loai, GhiChu, MaHocKy) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHanhKiem);
            ps.setString(2, maHocSinh);
            ps.setString(3, hanhKiem);
            ps.setString(4, ghiChu);
            ps.setString(5, maHocKy);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hạnh kiểm: " + e.getMessage());
            return false;
        }
    }

    private String generateHanhKiemCode() {
        String prefix = "HK";
        String sql = "SELECT COUNT(*) AS count FROM HanhKiem";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("count") + 1;
                return String.format("%s%03d", prefix, count);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã hạnh kiểm: " + e.getMessage());
        }
        return prefix + "001";
    }

    public List<String> getAllHanhKiemTypes() {
        List<String> hanhKiemList = new ArrayList<>();
        hanhKiemList.add("Tốt");
        hanhKiemList.add("Khá");
        hanhKiemList.add("Trung bình");
        hanhKiemList.add("Yếu");

        String sql = "SELECT DISTINCT Loai FROM HanhKiem WHERE Loai IS NOT NULL";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String loai = rs.getString("Loai");
                if (loai != null && !loai.isEmpty() && !hanhKiemList.contains(loai)) {
                    hanhKiemList.add(loai);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách loại hạnh kiểm: " + e.getMessage());
        }
        return hanhKiemList;
    }

    public List<Diem> getDiemByMaHocSinh(String maHocSinh) {
        List<Diem> diemList = new ArrayList<>();
        String sql = "SELECT * FROM DiemSo WHERE MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Diem diem = new Diem(
                    rs.getString("MaHocSinh"),
                    rs.getString("MaMonHoc"),
                    rs.getDouble("diem45Phut"),
                    rs.getDouble("diemGiuaKy"),
                    rs.getDouble("diemCuoiKy"),
                    rs.getString("MaHocKy"),
                    rs.getString("MoTa")
                );
                diem.setMa(rs.getString("Ma"));
                diem.setDiemTrungBinh(rs.getDouble("diemTrungBinh"));
                diem.setHocLuc(rs.getString("hocLuc"));
                diemList.add(diem);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy điểm theo mã học sinh: " + e.getMessage());
        }
        return diemList;
    }

    public String getHanhKiemByMaHocSinh(String maHocSinh, String maHocKy) {
        String sql = "SELECT Loai FROM HanhKiem WHERE MaHocSinh = ? AND MaHocKy = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ps.setString(2, maHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Loai");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy hạnh kiểm: " + e.getMessage());
        }
        return "N/A";
    }

    public String getGhiChuByMaHocSinh(String maHocSinh, String maHocKy) {
        String sql = "SELECT GhiChu FROM HanhKiem WHERE MaHocSinh = ? AND MaHocKy = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ps.setString(2, maHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("GhiChu");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy ghi chú: " + e.getMessage());
        }
        return "";
    }

    public String getTenHocSinh(String maHocSinh) {
        String sql = "SELECT HoTen FROM HocSinh WHERE MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("HoTen");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tên học sinh: " + e.getMessage());
        }
        return "";
    }

    public List<Object[]> getHanhKiemWithHocKyNamHoc(String maHocSinh) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT hk.MaHK AS MaHK, hs.HoTen, hk.Loai, hk.GhiChu, hk.MaHocKy " +
                     "FROM HanhKiem hk " +
                     "JOIN HocSinh hs ON hk.MaHocSinh = hs.MaHocSinh " +
                     "WHERE hk.MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getString("MaHK"),
                    rs.getString("HoTen"),
                    rs.getString("Loai"),
                    rs.getString("GhiChu"),
                    rs.getString("MaHocKy")
                };
                list.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy hạnh kiểm: " + e.getMessage());
        }
        return list;
    }

    public boolean insertNamHoc(NamHoc namHoc) {
        String sql = "SELECT COUNT(*) FROM NamHoc WHERE MaHocKy = ? AND MoTa != ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namHoc.getMaHocKy());
            ps.setString(2, namHoc.getMoTa());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.err.println("Học kỳ đã được gán cho năm học khác!");
                return false;
            }
            sql = "INSERT INTO NamHoc (MoTa, MaHocKy) VALUES (?, ?)";
            try (PreparedStatement psInsert = conn.prepareStatement(sql)) {
                psInsert.setString(1, namHoc.getMoTa());
                psInsert.setString(2, namHoc.getMaHocKy());
                return psInsert.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm năm học: " + e.getMessage());
            return false;
        }
    }

    public List<Lop> getLopHocByNamHoc(String moTaNamHoc) {
        List<Lop> lopList = new ArrayList<>();
        String sql = "SELECT * FROM LopHoc WHERE NienKhoa = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, moTaNamHoc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lop lop = new Lop();
                lop.setMaLop(rs.getString("MaLop"));
                lop.setTenLop(rs.getString("TenLop"));
                lop.setMaPhongHoc(rs.getString("Ma_Phong_hoc"));
                lop.setKhoi(rs.getInt("Khoi"));
                lop.setNamHoc(rs.getString("NienKhoa"));
                lop.setSiSo(rs.getInt("SiSo"));
                lop.setMaNguoiDung(rs.getString("Ma_nguoi_dung"));
                lopList.add(lop);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp theo năm học: " + e.getMessage());
        }
        return lopList;
    }

    public List<Lop> getLopHocByNamHocAndHocKy(String moTaNamHoc, String maHocKy) {
        List<Lop> lopList = new ArrayList<>();
        String sql = "SELECT DISTINCT lh.* FROM LopHoc lh " +
                     "JOIN HocSinh hs ON lh.MaLop = hs.MaLop " +
                     "JOIN DiemSo ds ON hs.MaHocSinh = ds.MaHocSinh " +
                     "WHERE lh.NienKhoa = ? AND ds.MaHocKy = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, moTaNamHoc);
            ps.setString(2, maHocKy);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lop lop = new Lop();
                lop.setMaLop(rs.getString("MaLop"));
                lop.setTenLop(rs.getString("TenLop"));
                lop.setMaPhongHoc(rs.getString("Ma_Phong_hoc"));
                lop.setKhoi(rs.getInt("Khoi"));
                lop.setNamHoc(rs.getString("NienKhoa"));
                lop.setSiSo(rs.getInt("SiSo"));
                lop.setMaNguoiDung(rs.getString("Ma_nguoi_dung"));
                lopList.add(lop);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp theo năm học và học kỳ: " + e.getMessage());
        }
        return lopList;
    }

    public boolean isDiemExists(String maHocSinh, String maMonHoc, String maHocKy, String moTaNamHoc) {
        String sql = "SELECT COUNT(*) FROM DiemSo WHERE MaHocSinh = ? AND MaMonHoc = ? AND MaHocKy = ? AND MoTa = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ps.setString(2, maMonHoc);
            ps.setString(3, maHocKy);
            ps.setString(4, moTaNamHoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra điểm tồn tại: " + e.getMessage());
        }
        return false;
    }

    public List<HocSinh> getAllHocSinh() {
        List<HocSinh> hocSinhList = new ArrayList<>();
        String sql = "SELECT MaHocSinh, HoTen FROM HocSinh";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HocSinh hocSinh = new HocSinh();
                hocSinh.setMaHocSinh(rs.getString("MaHocSinh"));
                hocSinh.setTenHocSinh(rs.getString("HoTen"));
                hocSinhList.add(hocSinh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách học sinh: " + e.getMessage());
        }
        return hocSinhList;
    }

    public boolean updateHanhKiem(String maHocSinh, String hanhKiem, String ghiChu, String maHocKy) {
        String sql = "UPDATE HanhKiem SET Loai = ?, GhiChu = ? WHERE MaHocSinh = ? AND MaHocKy = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hanhKiem);
            ps.setString(2, ghiChu);
            ps.setString(3, maHocSinh);
            ps.setString(4, maHocKy);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                return insertHanhKiem(maHocSinh, hanhKiem, ghiChu, maHocKy);
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật hạnh kiểm: " + e.getMessage());
            return false;
        }
    }

   public List<Object[]> getHocSinhByLopAndNamHoc(String maLop, String moTaNamHoc, String maHocKy) {
    List<Object[]> hocSinhList = new ArrayList<>();
    if (maLop == null || maLop.trim().isEmpty() || moTaNamHoc == null || moTaNamHoc.trim().isEmpty() || maHocKy == null || maHocKy.trim().isEmpty()) {
        System.err.println("Thông tin đầu vào không hợp lệ: maLop, moTaNamHoc hoặc maHocKy rỗng.");
        return hocSinhList;
    }
    
    String sql = "SELECT hs.HoTen, hs.MaHocSinh, COALESCE(AVG(ds.diemTrungBinh), 0.0) AS DiemTBTong, " +
                 "CASE " +
                 "    WHEN AVG(ds.diemTrungBinh) >= 8.0 THEN 'Giỏi' " +
                 "    WHEN AVG(ds.diemTrungBinh) >= 6.5 THEN 'Khá' " +
                 "    WHEN AVG(ds.diemTrungBinh) >= 5.0 THEN 'Trung bình' " +
                 "    ELSE 'Yếu' " +
                 "END AS XepLoai, COALESCE(hk.Loai, 'N/A') AS HanhKiem, COALESCE(hk.GhiChu, '') AS GhiChu, COALESCE(ds.MaHocKy, ?) AS MaHocKy " +
                 "FROM HocSinh hs " +
                 "JOIN LopHoc lh ON hs.MaLop = lh.MaLop " +
                 "LEFT JOIN DiemSo ds ON hs.MaHocSinh = ds.MaHocSinh AND ds.MaHocKy = ? AND ds.MoTa = ? " +
                 "LEFT JOIN HanhKiem hk ON hs.MaHocSinh = hk.MaHocSinh AND hk.MaHocKy = ? " +
                 "WHERE lh.MaLop = ? AND lh.NienKhoa = ? " +
                 "GROUP BY hs.HoTen, hs.MaHocSinh, hk.Loai, hk.GhiChu, ds.MaHocKy";
    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHocKy);
        ps.setString(2, maHocKy);
        ps.setString(3, moTaNamHoc);
        ps.setString(4, maHocKy);
        ps.setString(5, maLop);
        ps.setString(6, moTaNamHoc);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getString("HoTen"),
                    rs.getString("MaHocSinh"),
                    rs.getDouble("DiemTBTong") != 0 ? String.format("%.2f", rs.getDouble("DiemTBTong")) : "N/A",
                    rs.getString("XepLoai") != null ? rs.getString("XepLoai") : "N/A",
                    rs.getString("HanhKiem") != null ? rs.getString("HanhKiem") : "N/A",
                    rs.getString("GhiChu") != null ? rs.getString("GhiChu") : "",
                    rs.getString("MaHocKy") != null ? rs.getString("MaHocKy") : "N/A"
                };
                hocSinhList.add(row);
            }
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách học sinh: " + e.getMessage());
        e.printStackTrace();
    }
    return hocSinhList;
}

    public List<String> getUsedHocKyForHocSinh(String maHocSinh) {
        List<String> usedHocKyList = new ArrayList<>();
        String query = "SELECT DISTINCT MaHocKy FROM DiemSo WHERE MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maHocSinh);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usedHocKyList.add(rs.getString("MaHocKy"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usedHocKyList;
    }

    public String getNamHocByMaHocSinh(String maHocSinh) {
        if (maHocSinh == null || maHocSinh.trim().isEmpty()) {
        return null;
    }
        String sql = "SELECT lh.NienKhoa FROM LopHoc lh " +
                     "JOIN HocSinh hs ON lh.MaLop = hs.MaLop " +
                     "WHERE hs.MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nienKhoa = rs.getString("NienKhoa");
                if (nienKhoa != null) {
//                    System.out.println("Niên khóa tìm thấy: " + nienKhoa);
                    return nienKhoa;
                } else {
//                    System.err.println("Niên khóa là NULL cho MaHocSinh: " + maHocSinh);
                    return "Không có niên khóa";
                }
            } else {
//                System.err.println("Không tìm thấy niên khóa cho MaHocSinh: " + maHocSinh);
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy niên khóa: " + e.getMessage());
            return null;
        }
    }

    public String getMaLopByMaHocSinh(String maHocSinh) {
        String sql = "SELECT MaLop FROM HocSinh WHERE MaHocSinh = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHocSinh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MaLop");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy mã lớp theo mã học sinh: " + e.getMessage());
        }
        return null;
    }
    
// Lấy danh sách các môn chưa có đủ điểm cho học sinh
public List<MonHoc> getMonHocChuaDuDiem(String maHocSinh) {
    List<MonHoc> monHocList = getAllMonHoc();
    List<MonHoc> monHocChuaDuDiem = new ArrayList<>();
    String sql = "SELECT DISTINCT MaMonHoc FROM DiemSo WHERE MaHocSinh = ?";

    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHocSinh);
        ResultSet rs = ps.executeQuery();
        List<String> monHocDaCoDiem = new ArrayList<>();
        while (rs.next()) {
            monHocDaCoDiem.add(rs.getString("MaMonHoc"));
        }

        // Chỉ thêm các môn chưa có điểm hoặc thiếu điểm trong một số học kỳ
        for (MonHoc monHoc : monHocList) {
            if (!monHocDaCoDiem.contains(monHoc.getMaMonHoc())) {
                monHocChuaDuDiem.add(monHoc);
            }
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách môn học chưa đủ điểm: " + e.getMessage());
    }
    return monHocChuaDuDiem;
}

// Lấy danh sách học kỳ chưa có điểm cho cặp (học sinh, môn học)
public List<String> getHocKyChuaCoDiem(String maHocSinh, String maMonHoc) {
    List<String> hocKyChuaCoDiem = new ArrayList<>();
    String sql = "SELECT DISTINCT MaHocKy FROM DiemSo WHERE MaHocSinh = ? AND MaMonHoc = ?";

    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHocSinh);
        ps.setString(2, maMonHoc);
        ResultSet rs = ps.executeQuery();
        List<String> hocKyDaCoDiem = new ArrayList<>();
        while (rs.next()) {
            hocKyDaCoDiem.add(rs.getString("MaHocKy"));
        }

        // Lấy tất cả học kỳ
        List<HocKy> allHocKy = getAllHocKy();
        for (HocKy hocKy : allHocKy) {
            if (!hocKyDaCoDiem.contains(hocKy.getMaHocKy())) {
                hocKyChuaCoDiem.add(hocKy.getMaHocKy());
            }
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách học kỳ chưa có điểm: " + e.getMessage());
    }
    return hocKyChuaCoDiem;
}

public List<String> getHocKyDaCoDiem(String maHocSinh, String maMonHoc) {
    List<String> hocKyDaCoDiem = new ArrayList<>();
    String sql = "SELECT DISTINCT MaHocKy FROM DiemSo WHERE MaHocSinh = ? AND MaMonHoc = ?";

    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHocSinh);
        ps.setString(2, maMonHoc);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hocKyDaCoDiem.add(rs.getString("MaHocKy"));
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách học kỳ đã có điểm: " + e.getMessage());
    }
    return hocKyDaCoDiem;
}

}