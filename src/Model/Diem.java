package Model;

public class Diem {
    private String ma;
    private String maHocSinh;
    private String maMonHoc;
    private double diem45Phut;
    private double diemGiuaKy;
    private double diemCuoiKy;
    private double diemTrungBinh;
    private String hocLuc;
    private String maHocKy;
    private String moTa;

    public Diem() {
    }

    public Diem(String maHocSinh, String maMonHoc, double diem45Phut, double diemGiuaKy,
                double diemCuoiKy, String maHocKy, String moTa) {
        this.maHocSinh = maHocSinh;
        this.maMonHoc = maMonHoc;
        this.diem45Phut = diem45Phut;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.maHocKy = maHocKy;
        this.moTa = moTa;
        this.diemTrungBinh = calculateDiemTrungBinh();
        this.hocLuc = calculateHocLuc();
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public double getDiem45Phut() {
        return diem45Phut;
    }

    public void setDiem45Phut(double diem45Phut) {
        this.diem45Phut = diem45Phut;
        this.diemTrungBinh = calculateDiemTrungBinh();
        this.hocLuc = calculateHocLuc();
    }

    public double getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(double diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
        this.diemTrungBinh = calculateDiemTrungBinh();
        this.hocLuc = calculateHocLuc();
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
        this.diemTrungBinh = calculateDiemTrungBinh();
        this.hocLuc = calculateHocLuc();
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getHocLuc() {
        return hocLuc;
    }

    public void setHocLuc(String hocLuc) {
        this.hocLuc = hocLuc;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    private double calculateDiemTrungBinh() {
        return (diem45Phut * 0.2 + diemGiuaKy * 0.4 + diemCuoiKy * 0.4);
    }

    private String calculateHocLuc() {
        double diemTB = calculateDiemTrungBinh();
        if (diemTB >= 8.0) return "Giỏi";
        else if (diemTB >= 6.5) return "Khá";
        else if (diemTB >= 5.0) return "Trung bình";
        else return "Yếu";
    }
}