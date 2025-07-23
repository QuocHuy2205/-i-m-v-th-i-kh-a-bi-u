package Model;

public class HanhKiem {
    private String maHK;
    private String maHocSinh;
    private String loai;
    private String ghiChu;
    
    public HanhKiem() {
    }

    public HanhKiem(String maHK, String maHocSinh, String loai, String ghiChu) {
        this.maHK = maHK;
        this.maHocSinh = maHocSinh;
        this.loai = loai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public String getMaHK() {
        return maHK;
    }

    public void setMaHK(String maHK) {
        this.maHK = maHK;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "HanhKiem{" +
                "maHK='" + maHK + '\'' +
                ", maHocSinh='" + maHocSinh + '\'' +
                ", loai='" + loai + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}