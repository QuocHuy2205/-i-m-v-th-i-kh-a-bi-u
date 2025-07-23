package Controller;

import DAO.DiemvahanhkiemDAO;
import Model.Diem;
import Model.HocKy;
import Model.HocSinh;
import Model.Lop;
import Model.MonHoc;
import Model.NamHoc;
import java.util.ArrayList;
import java.util.List;

public class DiemVaHanhKiemController {
    private final DiemvahanhkiemDAO diemDAO;

    public DiemVaHanhKiemController() {
        diemDAO = new DiemvahanhkiemDAO();
    }

    public List<MonHoc> loadMonHoc() {
        return diemDAO.getAllMonHoc();
    }

    public List<HocSinh> loadHocSinh() {
        return diemDAO.getAllHocSinh();
    }

    public List<Lop> loadLop() {
        return diemDAO.getAllLop();
    }

    public List<HocKy> loadHocKy() {
        return diemDAO.getAllHocKy();
    }

    public List<NamHoc> loadNamHoc() {
        return diemDAO.getAllNamHoc();
    }

    public List<String> loadHanhKiemTypes() {
        return diemDAO.getAllHanhKiemTypes();
    }

    public List<Lop> getLopHocByNamHoc(String moTaNamHoc) {
        return diemDAO.getLopHocByNamHoc(moTaNamHoc);
    }

    public List<Lop> getLopHocByNamHocAndHocKy(String moTaNamHoc, String maHocKy) {
        return diemDAO.getLopHocByNamHocAndHocKy(moTaNamHoc, maHocKy);
    }

    public List<String> getUsedHocKyForHocSinh(String maHocSinh) {
        return diemDAO.getUsedHocKyForHocSinh(maHocSinh);
    }

    public List<Object[]> getTable1Data() {
        List<Object[]> data = new ArrayList<>();
        List<Diem> diemList = diemDAO.getAllDiem();
        for (Diem diem : diemList) {
            String hanhKiem = diemDAO.getHanhKiemByMaHocSinh(diem.getMaHocSinh(), diem.getMaHocKy());
            hanhKiem = (hanhKiem == null || hanhKiem.trim().isEmpty()) ? "N/A" : hanhKiem;
            String tenHS = diemDAO.getTenHocSinh(diem.getMaHocSinh());
            tenHS = (tenHS == null || tenHS.trim().isEmpty()) ? "N/A" : tenHS;
            String tenMH = getTenMonHoc(diem.getMaMonHoc());
            tenMH = (tenMH == null || tenMH.trim().isEmpty()) ? "N/A" : tenMH;
            String tenHK = getTenHocKy(diem.getMaHocKy());
            tenHK = (tenHK == null || tenHK.trim().isEmpty()) ? "N/A" : tenHK;
            String tenNH = diem.getMoTa() != null ? diem.getMoTa() : "N/A";
            String hocLuc = diem.getHocLuc() != null ? diem.getHocLuc() : "N/A";

            Object[] row = {
                diem.getMaHocSinh(),
                tenHS,
                tenMH,
                String.format("%.2f", diem.getDiem45Phut()),
                String.format("%.2f", diem.getDiemGiuaKy()),
                String.format("%.2f", diem.getDiemCuoiKy()),
                String.format("%.2f", diem.getDiemTrungBinh()),
                hocLuc,
                hanhKiem,
                tenHK,
                tenNH
            };
            data.add(row);
        }
        return data;
    }

 public List<Object[]> getTable2Data(String maLop, String moTaNamHoc, String maHocKy) {
    if (maLop == null || moTaNamHoc == null || maHocKy == null) {
        System.err.println("Thông tin đầu vào không hợp lệ trong getTable2Data.");
        return new ArrayList<>();
    }
    return diemDAO.getHocSinhByLopAndNamHoc(maLop, moTaNamHoc, maHocKy);
}

    public List<Object[]> getTable4Data(String maHocSinh, List<MonHoc> monHocList, String maHocKy) {
        List<Object[]> data = new ArrayList<>();
        List<Diem> diemList = diemDAO.getDiemByMaHocSinh(maHocSinh);
        List<String> missingMonHoc = new ArrayList<>();
        double tongDiemTB = 0.0;
        int monHocCount = 0;

        for (MonHoc monHoc : monHocList) {
            boolean hasDiem = false;
            for (Diem diem : diemList) {
                if (diem.getMaMonHoc().equals(monHoc.getMaMonHoc()) && (maHocKy == null || diem.getMaHocKy().equals(maHocKy))) {
                    double dtbMon = diem.getDiemTrungBinh();
                    Object[] row = {
                        monHoc.getTenMonHoc(),
                        String.format("%.2f", diem.getDiem45Phut()),
                        String.format("%.2f", diem.getDiemGiuaKy()),
                        String.format("%.2f", diem.getDiemCuoiKy()),
                        String.format("%.2f", dtbMon)
                    };
                    data.add(row);
                    tongDiemTB += dtbMon;
                    monHocCount++;
                    hasDiem = true;
                    break;
                }
            }
            if (!hasDiem) {
                missingMonHoc.add(monHoc.getTenMonHoc());
            }
        }

        String hanhKiem = diemDAO.getHanhKiemByMaHocSinh(maHocSinh, maHocKy);
        List<Object[]> result = new ArrayList<>();
        result.add(data.toArray(new Object[0][]));
        result.add(new Object[]{
            missingMonHoc,
            monHocCount > 0 ? String.format("%.2f", tongDiemTB / monHocCount) : "0.00",
            monHocCount > 0 ? (tongDiemTB / monHocCount >= 8.0 ? "Giỏi" : 
                               tongDiemTB / monHocCount >= 6.5 ? "Khá" : 
                               tongDiemTB / monHocCount >= 5.0 ? "Trung bình" : "Yếu") : "N/A",
            hanhKiem != null ? hanhKiem : "N/A"
        });
        return result;
    }

    public boolean addDiem(String maHocSinh, String maMonHoc, String maHocKy, String moTaNamHoc, float diem45, float diemGiuaKy, float diemCuoiKy) {
        if (!diemDAO.isHocSinhExists(maHocSinh)) {
            return false;
        }
        if (diemDAO.isDiemExists(maHocSinh, maMonHoc, maHocKy, moTaNamHoc)) {
            return false;
        }
        if (diem45 < 0 || diem45 > 10 || diemGiuaKy < 0 || diemGiuaKy > 10 || diemCuoiKy < 0 || diemCuoiKy > 10) {
            return false;
        }
        float diemTrungBinh = (float) (diem45 * 0.2 + diemGiuaKy * 0.4 + diemCuoiKy * 0.4);
        String hocLuc = diemTrungBinh >= 8.0 ? "Giỏi" : diemTrungBinh >= 6.5 ? "Khá" : diemTrungBinh >= 5.0 ? "Trung bình" : "Yếu";
        String maDiem = generateMaDiem();
        boolean namHocExists = diemDAO.getAllNamHoc().stream().anyMatch(nh -> nh.getMoTa().equals(moTaNamHoc));
        if (!namHocExists) {
            NamHoc newNamHoc = new NamHoc(moTaNamHoc, maHocKy);
            if (!diemDAO.insertNamHoc(newNamHoc)) {
                return false;
            }
        }
        boolean success = diemDAO.insertDiem(maDiem, maHocSinh, maMonHoc, diem45, diemGiuaKy, diemCuoiKy, diemTrungBinh, hocLuc, moTaNamHoc, maHocKy);
        if (success) {
            String hanhKiem = diemDAO.getHanhKiemByMaHocSinh(maHocSinh, maHocKy);
            if (hanhKiem == null || hanhKiem.trim().isEmpty()) {
                diemDAO.insertHanhKiem(maHocSinh, "Tốt", "", maHocKy);
            }
        }
        return success;
    }

    public boolean updateHanhKiem(String maHocSinh, String hanhKiem, String ghiChu, String maHocKy) {
        return diemDAO.updateHanhKiem(maHocSinh, hanhKiem, ghiChu, maHocKy);
    }

    public boolean isHocSinhExists(String maHocSinh) {
        return diemDAO.isHocSinhExists(maHocSinh);
    }

    private String generateMaDiem() {
        int maxId = 0;
        List<Diem> diemList = diemDAO.getAllDiem();
        for (Diem diem : diemList) {
            String ma = diem.getMa().replace("DS", "");
            try {
                int id = Integer.parseInt(ma);
                maxId = Math.max(maxId, id);
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return String.format("DS%03d", maxId + 1);
    }

    private String getTenMonHoc(String maMonHoc) {
        List<MonHoc> monHocList = diemDAO.getAllMonHoc();
        for (MonHoc monHoc : monHocList) {
            if (monHoc.getMaMonHoc().equals(maMonHoc)) {
                return monHoc.getTenMonHoc();
            }
        }
        return "N/A";
    }

    private String getTenHocKy(String maHocKy) {
        List<HocKy> hocKyList = diemDAO.getAllHocKy();
        for (HocKy hocKy : hocKyList) {
            if (hocKy.getMaHocKy().equals(maHocKy)) {
                return hocKy.getTenHocKy();
            }
        }
        return "N/A";
    }

    public String getNamHocByMaHocSinh(String maHocSinh) {
        return diemDAO.getNamHocByMaHocSinh(maHocSinh);
    }

    public String getMaLopByMaHocSinh(String maHocSinh) {
        return diemDAO.getMaLopByMaHocSinh(maHocSinh);
    }
    public List<HocKy> getAllHocKy() {
    return diemDAO.getAllHocKy();
}
    // Lấy danh sách môn chưa đủ điểm cho học sinh
public List<MonHoc> getMonHocChuaDuDiem(String maHocSinh) {
    return diemDAO.getMonHocChuaDuDiem(maHocSinh);
}

// Lấy danh sách học kỳ chưa có điểm cho cặp (học sinh, môn học)
public List<String> getHocKyChuaCoDiem(String maHocSinh, String maMonHoc) {
    return diemDAO.getHocKyChuaCoDiem(maHocSinh, maMonHoc);
}
public List<String> getHocKyDaCoDiem(String maHocSinh, String maMonHoc) {
    return diemDAO.getHocKyDaCoDiem(maHocSinh, maMonHoc);
}
 public List<Object[]> getHocSinhByLopAndNamHoc(String maHocKy, String maLop, String moTaNamHoc) {
        return getTable2Data(maLop, moTaNamHoc, maHocKy);
    }
 public float getDiemTrungBinh(String maHocSinh, String maHocKy, String moTaNamHoc) {
        // Lấy mã lớp từ mã HS nếu cần
        String maLop = getMaLopByMaHocSinh(maHocSinh);
        List<Object[]> data = getTable2Data(maLop, moTaNamHoc, maHocKy);
        for (Object[] row : data) {
            if (maHocSinh.equals(row[1])) {
                try {
                    return Float.parseFloat(row[2].toString());
                } catch (NumberFormatException e) {
                    return 0f;
                }
            }
        }
        return 0f;
    }
 
 public List<HocSinh> getHocSinhWithDiemByHocKy(String maHocKy, String maLop, String moTaNamHoc) {
    List<Object[]> raw = getTable2Data(maLop, moTaNamHoc, maHocKy);
    List<HocSinh> list = new ArrayList<>();
    for (Object[] row : raw) {
        HocSinh hs = new HocSinh();
        hs.setMaHocSinh((String)row[0]);
        hs.setTenHocSinh((String)row[1]);
        list.add(hs);
    }
    return list;
}

}