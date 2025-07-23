package Model;

public class NamHoc {
    private String moTa;
    private String maHocKy;

    public NamHoc() {
    }

    public NamHoc(String moTa, String maHocKy) {
        this.moTa = moTa;
        this.maHocKy = maHocKy;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    @Override
    public String toString() {
        return moTa;
    }
}