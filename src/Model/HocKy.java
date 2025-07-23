package Model;
import java.time.LocalDate;
public class HocKy {
   private String maHocKy;
    private String tenHocKy;
    private String batDau;
    private String ketThuc;

  
    public HocKy() {
    }

    public HocKy(String maHocKy, String tenHocKy, String batDau, String ketThuc) {
        this.maHocKy = maHocKy;
        this.tenHocKy = tenHocKy;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
    }

    // Getters and Setters
    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getTenHocKy() {
        return tenHocKy;
    }

    public void setTenHocKy(String tenHocKy) {
        this.tenHocKy = tenHocKy;
    }

    public String getBatDau() {
        return batDau;
    }

    public void setBatDau(String batDau) {
        this.batDau = batDau;
    }

    public String getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(String ketThuc) {
        this.ketThuc = ketThuc;
    }
    @Override
    public String toString() {
        return "HocKy{" +
                "maHocKy='" + maHocKy + '\'' +
                ", tenHocKy='" + tenHocKy + '\'' +
                ", batDau=" + batDau +
                ", ketThuc=" + ketThuc +
                '}';
    }
}
