package Model;

import java.util.Date;

public class ThoiKhoaBieu {
    private String maTKB;
    private String tenLop;
    private String tenNguoiDung;
    private String tenMonHoc;
    private Date ngay; // Đổi thành Date (từ sửa đổi trước)
    private String tiet;
    private String maPhongHoc; // Đổi từ tenPhongHoc thành maPhongHoc
    private String tenHocKy;

    // Constructor
    public ThoiKhoaBieu() {
    }

    // Getters và Setters
    public String getMaTKB() {
        return maTKB;
    }

    public void setMaTKB(String maTKB) {
        this.maTKB = maTKB;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getTiet() {
        return tiet;
    }

    public void setTiet(String tiet) {
        this.tiet = tiet;
    }

    public String getMaPhongHoc() {
        return maPhongHoc;
    }

    public void setMaPhongHoc(String maPhongHoc) {
        this.maPhongHoc = maPhongHoc;
    }

    public String getTenHocKy() {
        return tenHocKy;
    }

    public void setTenHocKy(String tenHocKy) {
        this.tenHocKy = tenHocKy;
    }
}