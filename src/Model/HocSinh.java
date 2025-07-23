package Model;

public class HocSinh {
    private String maHocSinh;
    private String HoTen; 
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String HanhKiem; 
    private String thongTinPhuHuynh; 
    private String maLop;

    public HocSinh(String maHocSinh, String HoTen, String gioiTinh, String ngaySinh, String diaChi, String HanhKiem, String thongTinPhuHuynh, String maLop) {
        this.maHocSinh = maHocSinh;
        this.HoTen = HoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.HanhKiem = HanhKiem;
        this.thongTinPhuHuynh = thongTinPhuHuynh;
        this.maLop = maLop;
    }

    public HocSinh() {
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getTenHocSinh() { // Giữ getter này để đồng bộ
        return HoTen;
    }

    public void setTenHocSinh(String HoTen) { // Sửa setter
        this.HoTen = HoTen;
    }

    public String getHanhKiem() {
        return HanhKiem;
    }

    public void setHanhKiem(String trangThai) {
        this.HanhKiem = trangThai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThongTinPhuHuynh() { 
        return thongTinPhuHuynh;
    }

    public void setThongTinPhuHuynh(String thongTinPhuHuynh) { 
        this.thongTinPhuHuynh = thongTinPhuHuynh;
    }   

    @Override
    public String toString() {
        return "HocSinh{" +
                "maHocSinh='" + maHocSinh + '\'' +
                ", HoTen='" + HoTen + '\'' + // Sử dụng HoTen
                ", trangThai='" + HanhKiem + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", ThongTinPhuHuynh='" + thongTinPhuHuynh + '\'' +
                '}';
    }
}