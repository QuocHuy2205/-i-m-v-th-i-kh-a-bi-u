package DAO;

import DBConnect.Connect;
import Model.HocKy;
import Model.HocSinh;
import Model.MonHoc;
import Model.ThoiKhoaBieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class thoiKhoaBieuDAO {
    
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
    public List<String> getAllHocKy() {
        List<String> hocKyList = new ArrayList<>();
        String sql = "SELECT TenHocKy FROM Hocky";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                hocKyList.add(rs.getString("TenHocKy"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách học kỳ: " + e.getMessage());
        }
        return hocKyList;
    }
    
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
     
    public List<String> getAllLopHoc() {
        List<String> lopHocList = new ArrayList<>();
        String sql = "SELECT TenLop FROM LopHoc";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lopHocList.add(rs.getString("TenLop"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp: " + e.getMessage());
        }
        return lopHocList;
    }
    
    public List<String> getAllNienKhoa() {
        List<String> nienKhoaList = new ArrayList<>();
        String sql = "SELECT DISTINCT NienKhoa FROM LopHoc ORDER BY NienKhoa";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nienKhoaList.add(rs.getString("NienKhoa"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách niên khóa: " + e.getMessage());
        }
        return nienKhoaList;
    }
    
    public List<String> getLopHocByNienKhoa(String nienKhoa) {
        List<String> lopHocList = new ArrayList<>();
        String sql = "SELECT TenLop FROM LopHoc WHERE NienKhoa = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nienKhoa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lopHocList.add(rs.getString("TenLop"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp theo niên khóa: " + e.getMessage());
        }
        return lopHocList;
    }
    
    public List<String> getAllGiaoVien() {
        List<String> giaoVienList = new ArrayList<>();
        String sql = "SELECT Ten_nguoi_dung FROM NguoiDung WHERE Ma_vai_tro IN ('GV', 'GVCN')";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                giaoVienList.add(rs.getString("Ten_nguoi_dung"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }
        return giaoVienList;
    }
    
public List<String> getAllMaPhongHoc() {
    List<String> phongHocList = new ArrayList<>();
    String sql = "SELECT Ma_Phong_hoc FROM PhongHoc"; // Thay bằng tên cột thực tế
    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            phongHocList.add(rs.getString("Ma_Phong_hoc")); // Thay bằng tên cột thực tế
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách mã phòng học: " + e.getMessage());
    }
    return phongHocList;
}
    
    public String generateNewMaTKB() {
        String sql = "SELECT TOP 1 Ma_TKB FROM ThoiKhoaBieu ORDER BY Ma_TKB DESC";
        String newMaTKB = "TKB001";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastMaTKB = rs.getString("Ma_TKB");
                if (lastMaTKB != null && lastMaTKB.startsWith("TKB")) {
                    int number = Integer.parseInt(lastMaTKB.replace("TKB", "")) + 1;
                    newMaTKB = String.format("TKB%03d", number);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo mã TKB: " + e.getMessage());
        }
        return newMaTKB;
    }
    
    public String getMaLopByTen(String tenLop) {
        String maLop = null;
        String sql = "SELECT MaLop FROM LopHoc WHERE TenLop = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLop);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maLop = rs.getString("MaLop");
                } else {
                    System.err.println("Không tìm thấy MaLop cho tên: " + tenLop);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy MaLop: " + e.getMessage());
        }
        return maLop;
    }
    
    public String getMaMonHocByTen(String tenMonHoc) {
        String maMonHoc = null;
        String sql = "SELECT MaMonHoc FROM MonHoc WHERE TenMonHoc = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenMonHoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maMonHoc = rs.getString("MaMonHoc");
                } else {
                    System.err.println("Không tìm thấy MaMonHoc cho tên: " + tenMonHoc);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy MaMonHoc: " + e.getMessage());
        }
        return maMonHoc;
    }
    
    public String getMaPhongHocByTen(String tenPhongHoc) {
        String maPhongHoc = null;
        String sql = "SELECT MaPhongHoc FROM PhongHoc WHERE TenPhongHoc = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenPhongHoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maPhongHoc = rs.getString("MaPhongHoc");
                } else {
                    System.err.println("Không tìm thấy MaPhongHoc cho tên: " + tenPhongHoc);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy MaPhongHoc: " + e.getMessage());
        }
        return maPhongHoc;
    }
    
    public String getMaHocKyByTen(String tenHocKy) {
        String maHocKy = null;
        String sql = "SELECT MaHocKy FROM Hocky WHERE TenHocKy = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maHocKy = rs.getString("MaHocKy");
                } else {
                    System.err.println("Không tìm thấy MaHocKy cho tên: " + tenHocKy);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy MaHocKy: " + e.getMessage());
        }
        return maHocKy;
    }
    
    public boolean isTeacherScheduleConflict(String tenNguoiDung, String ngay, String tiet) {
        String sql = "SELECT COUNT(*) FROM ThoiKhoaBieu WHERE Ten_nguoi_dung = ? AND Ngay = ? AND Tiet = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(ngay);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            
            ps.setString(1, tenNguoiDung);
            ps.setDate(2, sqlDate);
            ps.setString(3, tiet);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu đã có lịch trùng tiết trong ngày
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra trùng lịch giáo viên: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Lỗi khi chuyển đổi định dạng ngày: " + e.getMessage());
        }
        return false;
    }
    
    private boolean isValidNote(String note) {
        if (note == null || note.isEmpty()) {
            return true; // Cho phép ghi chú rỗng
        }
        // Chỉ cho phép chữ cái, số, khoảng trắng, dấu chấm, dấu phẩy
        String regex = "^[a-zA-Z0-9\\s\\.,]*$";
        return note.matches(regex);
    }
    
    public boolean saveThoiKhoaBieu(String maTKB, String tenLop, String tenNguoiDung, String tenMonHoc, 
                                    String ngay, String tiet, String maPhongHoc, String tenHocKy, String ghichu) {
        // Kiểm tra trùng lịch giáo viên
        if (isTeacherScheduleConflict(tenNguoiDung, ngay, tiet)) {
            System.err.println("Giáo viên " + tenNguoiDung + " đã được phân công dạy tiết " + tiet + " vào ngày " + ngay);
            return false;
        }

        // Kiểm tra số tiết hợp lệ
        try {
            int tietNum = Integer.parseInt(tiet);
            if (tietNum < 1 || tietNum > 10) {
                System.err.println("Số tiết phải từ 1 đến 10, giá trị nhập: " + tiet);
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Số tiết không hợp lệ, phải là số nguyên: " + tiet);
            return false;
        }

        // Kiểm tra ghi chú không chứa ký tự đặc biệt
        if (!isValidNote(ghichu)) {
            System.err.println("Ghi chú chứa ký tự không hợp lệ: " + ghichu);
            return false;
        }

        String sql = "INSERT INTO ThoiKhoaBieu (Ma_TKB, MaLop, Ten_nguoi_dung, MaMonHoc, Ngay, Tiet, Ma_Phong_hoc, MaHocKy, ghichu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTKB);
            String maLop = getMaLopByTen(tenLop);
            if (maLop == null) {
                System.err.println("MaLop không hợp lệ cho TenLop: " + tenLop);
                return false;
            }
            ps.setString(2, maLop);
            ps.setString(3, tenNguoiDung);
            String maMonHoc = getMaMonHocByTen(tenMonHoc);
            if (maMonHoc == null) {
                System.err.println("MaMonHoc không hợp lệ cho TenMonHoc: " + tenMonHoc);
                return false;
            }
            ps.setString(4, maMonHoc);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(ngay);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            ps.setDate(5, sqlDate);
            
            ps.setString(6, tiet);
            ps.setString(7, maPhongHoc);
            String maHocKy = getMaHocKyByTen(tenHocKy);
            if (maHocKy == null) {
                System.err.println("MaHocKy không hợp lệ cho TenHocKy: " + tenHocKy);
                return false;
            }
            ps.setString(8, maHocKy);
            ps.setString(9, ghichu);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu thời khóa biểu: " + e.getMessage());
            return false;
        } catch (ParseException e) {
            System.err.println("Lỗi khi chuyển đổi định dạng ngày: " + ngay + ", Lỗi: " + e.getMessage());
            return false;
        }
    }
    
//public boolean saveThoiKhoaBieu(String maTKB, String tenLop, String tenNguoiDung, String tenMonHoc, 
//                                String ngay, String tiet, String maPhongHoc, String tenHocKy, String ghichu) {
//    String sql = "INSERT INTO ThoiKhoaBieu (Ma_TKB, MaLop, Ten_nguoi_dung, MaMonHoc, Ngay, Tiet, Ma_Phong_hoc, MaHocKy, ghichu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    try (Connection conn = Connect.getConnection();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//        ps.setString(1, maTKB);
//        String maLop = getMaLopByTen(tenLop);
//        if (maLop == null) {
//            System.err.println("MaLop không hợp lệ cho TenLop: " + tenLop);
//            return false;
//        }
//        ps.setString(2, maLop);
//        ps.setString(3, tenNguoiDung);
//        String maMonHoc = getMaMonHocByTen(tenMonHoc);
//        if (maMonHoc == null) {
//            System.err.println("MaMonHoc không hợp lệ cho TenMonHoc: " + tenMonHoc);
//            return false;
//        }
//        ps.setString(4, maMonHoc);
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        sdf.setLenient(false);
//        Date parsedDate = sdf.parse(ngay);
//        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
//        ps.setDate(5, sqlDate);
//        
//        ps.setString(6, tiet);
//        ps.setString(7, maPhongHoc); // Sử dụng trực tiếp mã phòng học
//        String maHocKy = getMaHocKyByTen(tenHocKy);
//        if (maHocKy == null) {
//            System.err.println("MaHocKy không hợp lệ cho TenHocKy: " + tenHocKy);
//            return false;
//        }
//        ps.setString(8, maHocKy);
//        ps.setString(9, ghichu);
//        
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected > 0;
//    } catch (SQLException e) {
//        System.err.println("Lỗi khi lưu thời khóa biểu: " + e.getMessage());
//        return false;
//    } catch (ParseException e) {
//        System.err.println("Lỗi khi chuyển đổi định dạng ngày: " + ngay + ", Lỗi: " + e.getMessage());
//        return false;
//    }
//}
    
public List<ThoiKhoaBieu> getAllThoiKhoaBieu() {
    List<ThoiKhoaBieu> tkbList = new ArrayList<>();
    String sql = "SELECT Ma_TKB, MaLop, Ten_nguoi_dung, MaMonHoc, Ngay, Tiet, Ma_Phong_hoc, MaHocKy, ghichu FROM ThoiKhoaBieu";
    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            ThoiKhoaBieu tkb = new ThoiKhoaBieu();
            tkb.setMaTKB(rs.getString("Ma_TKB"));
            tkb.setTenLop(rs.getString("MaLop"));
            tkb.setTenNguoiDung(rs.getString("Ten_nguoi_dung"));
            tkb.setTenMonHoc(rs.getString("MaMonHoc"));
            java.sql.Date sqlDate = rs.getDate("Ngay");
            tkb.setNgay(sqlDate != null ? new Date(sqlDate.getTime()) : null); // Gán Date hoặc null
            tkb.setTiet(rs.getString("Tiet"));
            tkb.setMaPhongHoc(rs.getString("Ma_Phong_hoc"));
            tkb.setTenHocKy(rs.getString("MaHocKy"));
            tkbList.add(tkb);
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy danh sách thời khóa biểu: " + e.getMessage());
    }
    return tkbList;
}
    public String getMaPhongHocByTenPhong(String tenPhongHoc) {
    String maPhongHoc = null;
    String sql = "SELECT Ma_Phong_hoc FROM PhongHoc WHERE TenPhonghoc = ?";
    try (Connection conn = Connect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, tenPhongHoc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            maPhongHoc = rs.getString("Ma_Phong_hoc");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return maPhongHoc;
    }   
    
    public List<String> getGiaoVienByMonHoc(String tenMonHoc) {
        List<String> giaoVienList = new ArrayList<>();
        String sql = "SELECT DISTINCT Ten_nguoi_dung FROM NguoiDung WHERE BoMon = ? AND Ma_vai_tro IN ('GV', 'GVCN')";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenMonHoc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    giaoVienList.add(rs.getString("Ten_nguoi_dung"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách giáo viên theo môn học: " + e.getMessage());
        }
        return giaoVienList;
    }   
}