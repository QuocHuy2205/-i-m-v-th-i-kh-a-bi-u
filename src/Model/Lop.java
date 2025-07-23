package Model;
 
public class Lop {
   private String maLop;
    private String tenLop;
    private String maPhongHoc;
    private Integer khoi;
    private String namHoc;
    private Integer siSo;
    private String maNguoiDung;

    // Constructor mặc định
    public Lop() {
    }

    // Constructor với tất cả tham số
    public Lop(String maLop, String tenLop, String maPhongHoc, Integer khoi, String namHoc, Integer siSo, String maNguoiDung) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maPhongHoc = maPhongHoc;
        this.khoi = khoi;
        this.namHoc = namHoc;
        this.siSo = siSo;
        this.maNguoiDung = maNguoiDung;
    }

    // Getters and Setters
    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getMaPhongHoc() {
        return maPhongHoc;
    }

    public void setMaPhongHoc(String maPhongHoc) {
        this.maPhongHoc = maPhongHoc;
    }

    public Integer getKhoi() {
        return khoi;
    }

    public void setKhoi(Integer khoi) {
       if (khoi != null && (khoi < 10 || khoi > 12)) {
        throw new IllegalArgumentException("Khối phải từ 10 đến 12!");
    }
        this.khoi = khoi;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }

    public Integer getSiSo() {
        return siSo;
    }

    public void setSiSo(Integer siSo) {
        this.siSo = siSo;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

  @Override
    public String toString() {
        return this.tenLop;
    }
}
