
package QuanLiDiemVaHanhKiem;
import Controller.DiemVaHanhKiemController;
import Model.HocKy;
import Model.HocSinh;
import Model.Lop;
import Model.MonHoc;
import Model.NamHoc;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
public class QuanLiDiemVaHanhKiem extends javax.swing.JPanel {
     private final DiemVaHanhKiemController controller;
    private DefaultTableModel tableModel1;
    private DefaultTableModel tableModel2;
    private DefaultTableModel tableModel4;
    private List<Lop> lopList;
    private List<HocKy> hocKyList;
    private List<NamHoc> namHocList;
    private List<MonHoc> monHocList;
    private List<HocSinh> hocSinhList;
    private String selectedMaHocSinh;

    public QuanLiDiemVaHanhKiem() {
        controller = new DiemVaHanhKiemController();
        monHocList = new ArrayList<>();
        lopList = new ArrayList<>();
        hocKyList = new ArrayList<>();
        namHocList = new ArrayList<>();
        hocSinhList = new ArrayList<>();
        initComponents();
        initTableModels();
        loadComboBoxData();
        TxtMa.setEnabled(false); 
        txtMaLop.setEnabled(false);
        fillToTable1();
        cbbNamHoc.setEnabled(false);
    }

private void initTableModels() {
    tableModel1 = new DefaultTableModel(
        new Object[][]{},
        new String[]{"Mã học sinh", "Tên học sinh", "Môn học", "Điểm 45'", "Điểm giữa kỳ", "Điểm cuối kỳ", "Điểm TB", "Học lực", "Hạnh kiểm", "Học kỳ", "Năm học"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    jTable1.setModel(tableModel1);

    tableModel2 = new DefaultTableModel(
        new Object[][]{},
        new String[]{"Tên học sinh", "Mã học sinh", "Điểm TB tổng", "Học lực", "Hạnh kiểm", "Ghi chú", "Học kỳ"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5; // Chỉ cột Ghi chú có thể chỉnh sửa
        }
    };
    jTable2.setModel(tableModel2);

    tableModel4 = new DefaultTableModel(
        new Object[][]{},
        new String[]{"Môn học", "Điểm 45'", "Điểm Giữa kỳ", "Điểm cuối kỳ", "ĐTB Môn"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    jTable4.setModel(tableModel4);
}

    private void loadComboBoxData() {
        // Load danh sách môn học
        CbbMonHoc.removeAllItems();
        CbbMonHoc.addItem("Chọn môn học");
        monHocList = controller.loadMonHoc();
        for (MonHoc monHoc : monHocList) {
            CbbMonHoc.addItem(monHoc.getTenMonHoc());
        }
        CbbMonHoc.setSelectedIndex(0);
// Load danh sách học kỳ
        CbbHocKy.removeAllItems();
        CbbHocKy.addItem("Chọn học kỳ");
        hocKyList = controller.loadHocKy();
        for (HocKy hocKy : hocKyList) {
            CbbHocKy.addItem(hocKy.getMaHocKy()); // Load mã học kỳ thay vì tên học kỳ
        }
        CbbHocKy.setSelectedIndex(0);
        // Load danh sách học kỳ cho tìm kiếm
        cbbHocKy.removeAllItems();
        cbbHocKy.addItem("Chọn học kỳ");
        for (HocKy hocKy : hocKyList) {
            cbbHocKy.addItem(hocKy.getMaHocKy()); // Load mã học kỳ thay vì tên học kỳ
        }
        cbbHocKy.setSelectedIndex(0);
        
        // Load danh sách học kỳ cho tìm kiếm
        cbbHk.removeAllItems();
        cbbHk.addItem("Chọn học kỳ");
        for (HocKy hocKy : hocKyList) {
            cbbHk.addItem(hocKy.getMaHocKy());
        }
        cbbHk.setSelectedIndex(0);
        
//        cbbNamHoc.removeAllItems();
//        cbbNamHoc.addItem("Chọn năm học");
//        namHocList = controller.loadNamHoc();
//        for (NamHoc namHoc : namHocList) {
//            cbbNamHoc.addItem(namHoc.getMoTa());
//        }
//        cbbNamHoc.setSelectedIndex(0);
        
        CbbNamHoc.removeAllItems();
        CbbNamHoc.addItem("Chọn năm học");
        namHocList = controller.loadNamHoc();
        for (NamHoc namHoc : namHocList) {
            CbbNamHoc.addItem(namHoc.getMoTa());
        }
        CbbNamHoc.setSelectedIndex(0);

        CbbHanhKiem.removeAllItems();
        List<String> hanhKiemList = controller.loadHanhKiemTypes();
        for (String hanhKiem : hanhKiemList) {
            CbbHanhKiem.addItem(hanhKiem);
        }
        CbbHanhKiem.setSelectedIndex(-1);

        CbbTen.removeAllItems();
        CbbTen.addItem("Chọn học sinh");
        hocSinhList = controller.loadHocSinh();
        if (hocSinhList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có học sinh nào trong cơ sở dữ liệu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            CbbTen.setEnabled(false);
        } else {
            for (HocSinh hocSinh : hocSinhList) {
                if (hocSinh.getTenHocSinh() != null && !hocSinh.getTenHocSinh().isEmpty()) {
                    CbbTen.addItem(hocSinh.getTenHocSinh());
                }
            }
            CbbTen.setEnabled(true);
        }
        CbbTen.setSelectedIndex(0);
        // Load danh sách lớp học
        updateLopHocComboBox(null, null);
    }
    private void updateCbbHocKy(String maHocSinh) {
    CbbHocKy.removeAllItems();
    CbbHocKy.addItem("Chọn học kỳ");

    // Lấy danh sách tất cả học kỳ
    List<HocKy> allHocKy = controller.getAllHocKy();
    
    if (maHocSinh != null && !maHocSinh.isEmpty()) {
        // Lấy danh sách học kỳ đã sử dụng bởi học sinh
        List<String> usedHocKy = controller.getUsedHocKyForHocSinh(maHocSinh);

        // Lọc các học kỳ chưa được sử dụng
        for (HocKy hocKy : allHocKy) {
            if (hocKy.getMaHocKy() != null && !usedHocKy.contains(hocKy.getMaHocKy())) {
                CbbHocKy.addItem(hocKy.getMaHocKy());
            }
        }
    } else {
        // Nếu không có học sinh được chọn, hiển thị tất cả học kỳ
        for (HocKy hocKy : allHocKy) {
            if (hocKy.getMaHocKy() != null) {
                CbbHocKy.addItem(hocKy.getMaHocKy());
            }
        }
    }

    CbbHocKy.setSelectedIndex(0);
}
private void updateCbbHk() {
    cbbHk.removeAllItems();
    cbbHk.addItem("Chọn học kỳ");
    List<HocKy> hocKyList = controller.getAllHocKy();
    for (HocKy hocKy : hocKyList) {
        if (hocKy.getMaHocKy() != null && !hocKy.getMaHocKy().isEmpty()) {
            cbbHk.addItem(hocKy.getMaHocKy());
        }
    }
    cbbHk.setEnabled(false);
    cbbHk.setSelectedIndex(0);
}
private void updateLopHocComboBox(String moTaNamHoc, String maHocKy) {
    Cbblop.removeAllItems();
    Cbblop.addItem("Chọn lớp học");
    if (moTaNamHoc != null && !moTaNamHoc.equals("Chọn năm học") && maHocKy != null && !maHocKy.equals("Chọn học kỳ")) {
        lopList = controller.getLopHocByNamHocAndHocKy(moTaNamHoc, maHocKy);
        for (Lop lop : lopList) {
            Cbblop.addItem(lop.getTenLop());
        }
        Cbblop.setEnabled(true);
    } else {
        Cbblop.setEnabled(false);
    }
    Cbblop.setSelectedIndex(0);
    tableModel2.setRowCount(0);
    jLabel14.setText("N/A");
    jLabel16.setText("N/A");
    jLabel18.setText("N/A");
    jLabel21.setText("N/A");
    jLabel22.setText("N/A");
}

    private void fillToTable1() {
    tableModel1.setRowCount(0);
    List<Object[]> data = controller.getTable1Data();
    for (Object[] row : data) {
        tableModel1.addRow(row);
    }
}

    private void fillToTable2() {
//    tableModel2.setRowCount(0);
//    int selectedIndex = Cbblop.getSelectedIndex();
//    String selectedNamHoc = CbbNamHoc.getSelectedItem() != null ? CbbNamHoc.getSelectedItem().toString() : null;
//    String maHocKy = cbbHk.getSelectedIndex() > 0 ? cbbHk.getSelectedItem().toString() : null;
//
//    if (selectedIndex > 0 && selectedNamHoc != null && !selectedNamHoc.equals("Chọn năm học") && maHocKy != null && !maHocKy.equals("Chọn học kỳ")) {
//        String maLop = lopList.get(selectedIndex - 1).getMaLop();
//        List<Object[]> dataList = controller.getTable2Data(maLop, selectedNamHoc, maHocKy);
//
//        if (dataList.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không có dữ liệu cho lớp, năm học và học kỳ này!", 
//                                         "Thông báo", JOptionPane.WARNING_MESSAGE);
//        }
//
//        int tongSoHocSinh = dataList.size();
//        int hocSinhGioi = 0, hocSinhKha = 0, hocSinhTrungBinh = 0, hocSinhYeu = 0;
//
//        for (Object[] row : dataList) {
//            String diemTBTongStr = (String) row[2];
//            if (!diemTBTongStr.equals("N/A")) {
//                try {
//                    double diemTBTong = Double.parseDouble(diemTBTongStr);
//                    if (diemTBTong >= 8.0) {
//                        hocSinhGioi++;
//                    } else if (diemTBTong >= 6.5) {
//                        hocSinhKha++;
//                    } else if (diemTBTong >= 5.0) {
//                        hocSinhTrungBinh++;
//                    } else {
//                        hocSinhYeu++;
//                    }
//                } catch (NumberFormatException e) {
//                    row[3] = "N/A";
//                    row[4] = "N/A";
//                }
//            } else {
//                row[3] = "N/A";
//                row[4] = "N/A";
//            }
//            tableModel2.addRow(row);
//        }
//
//        jLabel14.setText(String.valueOf(tongSoHocSinh));
//        jLabel16.setText(String.valueOf(hocSinhGioi));
//        jLabel18.setText(String.valueOf(hocSinhKha));
//        jLabel21.setText(String.valueOf(hocSinhTrungBinh));
//        jLabel22.setText(String.valueOf(hocSinhYeu));
//    } else {
//        JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp, năm học và học kỳ!", 
//                                     "Lỗi", JOptionPane.ERROR_MESSAGE);
//        jLabel14.setText("N/A");
//        jLabel16.setText("N/A");
//        jLabel18.setText("N/A");
//        jLabel21.setText("N/A");
//        jLabel22.setText("N/A");
//    }
  DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

    String maLop = Cbblop.getSelectedItem() != null && !Cbblop.getSelectedItem().equals("Chọn lớp") ? Cbblop.getSelectedItem().toString() : "";
    String maHocKy = cbbHk.getSelectedItem() != null && !cbbHk.getSelectedItem().equals("Chọn học kỳ") ? cbbHk.getSelectedItem().toString() : "";
    String moTaNamHoc = CbbNamHoc.getSelectedItem() != null && !CbbNamHoc.getSelectedItem().equals("Chọn năm học") ? CbbNamHoc.getSelectedItem().toString() : "";

    if (maLop.isEmpty() || maHocKy.isEmpty() || moTaNamHoc.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ lớp, học kỳ và năm học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy danh sách học sinh có điểm trong học kỳ, lớp và năm học được chọn
    List<HocSinh> hocSinhList = controller.getHocSinhWithDiemByHocKy(maHocKy, maLop, moTaNamHoc);

    if (hocSinhList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không có học sinh nào có điểm trong học kỳ và lớp đã chọn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Cập nhật bảng
    for (HocSinh hocSinh : hocSinhList) {
        if (hocSinh.getMaHocSinh() != null && hocSinh.getTenHocSinh() != null) {
            // Tính điểm trung bình (nếu cần)
            float diemTB = controller.getDiemTrungBinh(hocSinh.getMaHocSinh(), maHocKy, moTaNamHoc);
            Object[] row = {
                hocSinh.getMaHocSinh(),
                hocSinh.getTenHocSinh(),
                diemTB
            };
            model.addRow(row);
        }
    }
}
    

    private void fillToTable4(String maHocSinh) {
          tableModel4.setRowCount(0);
        String maHocKy = cbbHocKy.getSelectedIndex() > 0 ? hocKyList.get(cbbHocKy.getSelectedIndex() - 1).getMaHocKy() : null;
        List<Object[]> result = controller.getTable4Data(maHocSinh, monHocList, maHocKy);
        Object[][] data = (Object[][]) result.get(0);
        List<String> missingMonHoc = (List<String>) result.get(1)[0];
        String hanhKiem = (String) result.get(1)[2];

        // Tính điểm trung bình tổng từ các môn có điểm
        double totalDiemTB = 0.0;
        int countMonHoc = 0;
        for (Object[] row : data) {
            String diemTBMonStr = (String) row[4]; // Cột ĐTB Môn
            if (diemTBMonStr != null && !diemTBMonStr.isEmpty() && !diemTBMonStr.equals("N/A")) {
                try {
                    double diemTBMon = Double.parseDouble(diemTBMonStr);
                    totalDiemTB += diemTBMon;
                    countMonHoc++;
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu điểm không hợp lệ
                }
            }
            tableModel4.addRow(row);
        }

        // Tính điểm trung bình tổng và cập nhật jLabel32
        String diemTBTong;
        if (countMonHoc > 0) {
            diemTBTong = String.format("%.1f", totalDiemTB / countMonHoc);
        } else {
            diemTBTong = "N/A";
        }
        jLabel32.setText(diemTBTong);

        // Xác định học lực
        String hocLuc;
        if (!missingMonHoc.isEmpty()) {
            hocLuc = "Yếu"; // Nếu thiếu bất kỳ môn nào, học lực là Yếu
        } else {
            try {
                double diemTB = Double.parseDouble(diemTBTong);
                if (diemTB >= 8.0) {
                    hocLuc = "Giỏi";
                } else if (diemTB >= 6.5) {
                    hocLuc = "Khá";
                } else if (diemTB >= 5.0) {
                    hocLuc = "Trung bình";
                } else {
                    hocLuc = "Yếu";
                }
            } catch (NumberFormatException e) {
                hocLuc = "N/A";
            }
        }
        jLabel33.setText(hocLuc);

        // Cập nhật danh sách môn thiếu và hạnh kiểm
        if (!missingMonHoc.isEmpty()) {
            jLabelThieuDiem.setText("Thiếu điểm của các môn: " + String.join(", ", missingMonHoc));
        } else {
            jLabelThieuDiem.setText("");
        }
        jLabel34.setText(hanhKiem != null ? hanhKiem : "N/A");
    }
    
private void updateCbbHocKy() {
    cbbHk.removeAllItems();
    cbbHk.addItem("Chọn học kỳ");
    List<HocKy> hocKyList = controller.getAllHocKy();
    for (HocKy hocKy : hocKyList) {
        if (hocKy.getMaHocKy() != null && !hocKy.getMaHocKy().isEmpty()) {
            cbbHk.addItem(hocKy.getMaHocKy());
        }
    }
    cbbHk.setEnabled(false);
    cbbHk.setSelectedIndex(0);
}

private void updateCbbNamHoc(String maHocSinh) {
    cbbNamHoc.removeAllItems();
    if (maHocSinh != null && !maHocSinh.trim().isEmpty()) {
        String moTaNamHoc = controller.getNamHocByMaHocSinh(maHocSinh);
        if (moTaNamHoc != null && !moTaNamHoc.equals("Không có niên khóa")) {
            cbbNamHoc.addItem(moTaNamHoc);
            cbbNamHoc.setEnabled(true);
        } else {
            cbbNamHoc.addItem("Không có niên khóa");
            cbbNamHoc.setEnabled(false);
        }
    } else {
        cbbNamHoc.addItem("Chọn niên khóa");
        cbbNamHoc.setEnabled(false);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TxtMa = new javax.swing.JTextField();
        TxtBL = new javax.swing.JTextField();
        TxtGiuaKy = new javax.swing.JTextField();
        CbbMonHoc = new javax.swing.JComboBox<>();
        TxtCuoiKy = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnIn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        CbbHocKy = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        CbbTen = new javax.swing.JComboBox<>();
        cbbNamHoc = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        txtMaLop = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Cbblop = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtTenHS = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        CbbHanhKiem = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        CbbNamHoc = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cbbHocKy = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        cbbHk = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabelThieuDiem = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jPanel1.setBackground(new java.awt.Color(249, 250, 251));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setText("Nhập điểm học sinh");

        jLabel3.setText("Tên học sinh:");

        jLabel4.setText("Mã học sinh:");

        jLabel5.setText("Môn học:");

        jLabel6.setText("Điểm 45' (20%):");

        jLabel7.setText("Điểm giữa kỳ (40%): ");

        jLabel8.setText("Điểm cuối kỳ (40%):");

        CbbMonHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbbMonHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbMonHocActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(34, 197, 94));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Them diem");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(29, 78, 216));
        jLabel1.setText(" Hệ thống Quản lý Điểm & Hạnh kiểm ");

        btnIn.setBackground(new java.awt.Color(99, 102, 241));
        btnIn.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnIn.setForeground(new java.awt.Color(255, 255, 255));
        btnIn.setText("In bảng điểm cá nhân");
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel10.setText("Chức năng in ấn");

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel9.setText("Kết quả học tập và rèn luyện");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên học sinh", "Mã học sinh", "Môn học", "Điểm 45'", "Điểm giữa kỳ", "Điểm cuối kỳ", "Điểm trung bình ", "Học lực", "Hạnh kiểm "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel35.setText("Học kỳ:");

        CbbHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel36.setText("Năm học:");

        CbbTen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbbTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbTenActionPerformed(evt);
            }
        });

        cbbNamHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel38.setText("Mã lớp:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CbbMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(168, 168, 168)
                                .addComponent(jLabel1))
                            .addComponent(TxtBL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIn)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(TxtCuoiKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(310, 310, 310)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(txtMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(113, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(CbbTen, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbNamHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TxtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel35)
                    .addComponent(jLabel7)
                    .addComponent(TxtGiuaKy, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jLabel36)
                    .addComponent(CbbHocKy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(140, 140, 140))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel6, jLabel7, jLabel8});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {TxtBL, TxtCuoiKy, TxtGiuaKy, TxtMa, txtMaLop});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel2, jLabel9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CbbTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CbbMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CbbHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtBL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtGiuaKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtCuoiKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(33, 33, 33)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(btnIn)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel6, jLabel7, jLabel8});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {TxtBL, TxtCuoiKy, TxtGiuaKy, TxtMa, txtMaLop});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel2, jLabel9});

        jTabbedPane2.addTab("Giáo viên bộ môn", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel11.setText("Tổng quan lớp học");

        jLabel12.setText("Chọn lớp:");

        Cbblop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Cbblop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbblopActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tên học sinh", "Mã học sinh ", "Điểm trung bình tổng", "học lực ", "Hạnh kiểm", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel13.setText("Tổng số học sinh lớp :");

        jLabel14.setText("0");

        jLabel15.setText("Học sinh giỏi:");

        jLabel16.setText("0");

        jLabel17.setText("Học sinh khá:");

        jLabel18.setText("0");

        jLabel19.setText("Học sinh trung bình:");

        jLabel20.setText("Học sinh yếu:");

        jLabel21.setText("0");

        jLabel22.setText("0");

        jButton2.setText("Thêm hạnh kiểm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Xem chi tiết");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel26.setText("Tên học sinh:");

        jLabel27.setText("Chọn hạnh kiểm:");

        CbbHanhKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setText("Ghi chú:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        jLabel37.setText("Năm học:");

        CbbNamHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbbNamHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbNamHocActionPerformed(evt);
            }
        });

        jLabel24.setText("Học kỳ:");

        cbbHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel39.setText("Học Kỳ:");

        cbbHk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbHk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Cbblop, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(48, 48, 48)
                                .addComponent(jButton4)
                                .addGap(60, 60, 60)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbHk, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CbbNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel21))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel22))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel18)))
                                .addGap(76, 76, 76)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTenHS, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CbbHanhKiem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel28)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                            .addComponent(cbbHocKy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))))
                .addContainerGap(277, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel13, jLabel15, jLabel17, jLabel19, jLabel20});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CbbHanhKiem, jScrollPane5, txtTenHS});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CbbNamHoc, Cbblop});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel11)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cbblop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CbbNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbHk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel37)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtTenHS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(CbbHanhKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jButton2)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(cbbHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jLabel15, jLabel17, jLabel19, jLabel20});

        jTabbedPane2.addTab("Giáo viên chủ nhiệm", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setText("Học sinh:");

        jLabel25.setText("jLabel25");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Môn học", "Điểm 45'", "Điểm Giữa kỳ", "Điểm cuối kỳ", "ĐTB Môn"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jLabel29.setText("Điểm trung bình tổng:");

        jLabel30.setText("học lực:");

        jLabel31.setText("Hạnh kiểm");

        jLabel32.setText("jLabel32");

        jLabel33.setText("jLabel33");

        jLabel34.setText("jLabel34");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addGap(200, 200, 200)
                        .addComponent(jLabelThieuDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(302, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel25)
                    .addComponent(jLabelThieuDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34))
                .addContainerGap(372, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("chi tiết điểm số học sinh", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CbbMonHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbMonHocActionPerformed
String selectedTen = (String) CbbTen.getSelectedItem();
    String selectedMonHoc = (String) CbbMonHoc.getSelectedItem();
    if (selectedTen != null && !selectedTen.equals("Chọn học sinh") && selectedMonHoc != null && !selectedMonHoc.equals("Chọn môn học")) {
        for (HocSinh hocSinh : hocSinhList) {
            if (hocSinh.getTenHocSinh() != null && hocSinh.getTenHocSinh().equals(selectedTen)) {
                String maHocSinh = hocSinh.getMaHocSinh();
                String maMonHoc = null;
                for (MonHoc monHoc : monHocList) {
                    if (monHoc.getTenMonHoc().equals(selectedMonHoc)) {
                        maMonHoc = monHoc.getMaMonHoc();
                        break;
                    }
                }
                if (maMonHoc != null) {
                    // Cập nhật CbbHocKy với các học kỳ chưa có điểm
                    CbbHocKy.removeAllItems();
                    CbbHocKy.addItem("Chọn học kỳ");
                    List<String> hocKyChuaCoDiem = controller.getHocKyChuaCoDiem(maHocSinh, maMonHoc);
                    for (String maHocKy : hocKyChuaCoDiem) {
                        CbbHocKy.addItem(maHocKy);
                    }
                    CbbHocKy.setSelectedIndex(0);

                    // (Tùy chọn) Hiển thị thông báo nếu môn đã có điểm ở một số học kỳ
                    List<String> hocKyDaCoDiem = controller.getHocKyDaCoDiem(maHocSinh, maMonHoc);
                    if (!hocKyDaCoDiem.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Môn học này đã có điểm ở: " + hocKyDaCoDiem, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break;
            }
        }
    } else {
        CbbHocKy.removeAllItems();
        CbbHocKy.addItem("Chọn học kỳ");
        CbbHocKy.setSelectedIndex(0);
    }
    }//GEN-LAST:event_CbbMonHocActionPerformed

    private void CbblopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbblopActionPerformed
//1
//   String moTaNamHoc = CbbNamHoc.getSelectedItem() != null && !CbbNamHoc.getSelectedItem().equals("Chọn năm học") 
//                           ? CbbNamHoc.getSelectedItem().toString() 
//                           : "";
//        if (moTaNamHoc.isEmpty()) {
//            return;
//        }
//
//        String maLop = Cbblop.getSelectedItem() != null && !Cbblop.getSelectedItem().equals("Chọn lớp") 
//                      ? lopList.get(Cbblop.getSelectedIndex() - 1).getMaLop() 
//                      : "";
//        if (maLop.isEmpty()) {
//            tableModel2.setRowCount(0);
//            return;
//        }
//
//        String maHocKy = cbbHocKy.getSelectedIndex() > 0 ? hocKyList.get(cbbHocKy.getSelectedIndex() - 1).getMaHocKy() : null;
//        List<Object[]> data = controller.getTable2Data(maLop, moTaNamHoc, maHocKy);
//        tableModel2.setRowCount(0);
//        for (Object[] row : data) {
//            tableModel2.addRow(row);
//        }
//2
    if (Cbblop.getSelectedIndex() > 0) {
        fillToTable2();
    } else {
        tableModel2.setRowCount(0);
        jLabel14.setText("N/A");
        jLabel16.setText("N/A");
        jLabel18.setText("N/A");
        jLabel21.setText("N/A");
        jLabel22.setText("N/A");
    }
    }//GEN-LAST:event_CbblopActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        String maHocSinh = TxtMa.getText().trim();
//        if (maHocSinh.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (CbbMonHoc.getSelectedIndex() <= 0) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (CbbHocKy.getSelectedIndex() <= 0) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn học kỳ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        String moTaNamHoc = cbbNamHoc.getSelectedItem() != null && !cbbNamHoc.getSelectedItem().equals("Chọn năm học") ? cbbNamHoc.getSelectedItem().toString() : "";
//        if (moTaNamHoc.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String diem45Str = TxtBL.getText().trim();
//        String diemGiuaKyStr = TxtGiuaKy.getText().trim();
//        String diemCuoiKyStr = TxtCuoiKy.getText().trim();
//        String regex = "^(10(\\.0)?|[0-9](\\.\\d{0,1})?)$";
//
//        if (diem45Str.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Điểm 45 phút không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (diemGiuaKyStr.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Điểm giữa kỳ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (diemCuoiKyStr.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Điểm cuối kỳ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (!diem45Str.matches(regex)) {
//            JOptionPane.showMessageDialog(this, "Điểm 45 phút phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (!diemGiuaKyStr.matches(regex)) {
//            JOptionPane.showMessageDialog(this, "Điểm giữa kỳ phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (!diemCuoiKyStr.matches(regex)) {
//            JOptionPane.showMessageDialog(this, "Điểm cuối kỳ phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            float diem45 = Float.parseFloat(diem45Str);
//            float diemGiuaKy = Float.parseFloat(diemGiuaKyStr);
//            float diemCuoiKy = Float.parseFloat(diemCuoiKyStr);
//
//            if (diem45 < 0.0 || diem45 > 10.0) {
//                JOptionPane.showMessageDialog(this, "Điểm 45 phút phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (diemGiuaKy < 0.0 || diemGiuaKy > 10.0) {
//                JOptionPane.showMessageDialog(this, "Điểm giữa kỳ phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (diemCuoiKy < 0.0 || diemCuoiKy > 10.0) {
//                JOptionPane.showMessageDialog(this, "Điểm cuối kỳ phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            String maMonHoc = monHocList.get(CbbMonHoc.getSelectedIndex() - 1).getMaMonHoc();
//            String maHocKy = CbbHocKy.getSelectedItem().toString();
//
//            // Kiểm tra maHocKy có hợp lệ
//            boolean validHocKy = false;
//            for (HocKy hocKy : hocKyList) {
//                if (hocKy.getMaHocKy().equals(maHocKy)) {
//                    validHocKy = true;
//                    break;
//                }
//            }
//            if (!validHocKy) {
//                JOptionPane.showMessageDialog(this, "Học kỳ không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thêm điểm cho học sinh với mã " + maHocSinh + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//            if (confirm != JOptionPane.YES_OPTION) {
//                return;
//            }
//
//            boolean success = controller.addDiem(maHocSinh, maMonHoc, maHocKy, moTaNamHoc, diem45, diemGiuaKy, diemCuoiKy);
//            if (success) {
//                JOptionPane.showMessageDialog(this, "Thêm điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                fillToTable1();
//                updateCbbHocKy(maHocSinh);
//                TxtMa.setText("");
//                TxtBL.setText("");
//                TxtGiuaKy.setText("");
//                TxtCuoiKy.setText("");
//                CbbMonHoc.setSelectedIndex(0);
//                CbbHocKy.setSelectedIndex(0);
//                cbbNamHoc.setSelectedIndex(0);
//                CbbTen.setSelectedIndex(0);
//            } else {
//                JOptionPane.showMessageDialog(this, "Thêm điểm thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ (số thực)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }

String maHocSinh = TxtMa.getText().trim();
    if (maHocSinh.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (CbbMonHoc.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (CbbHocKy.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn học kỳ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
   if (cbbNamHoc.getSelectedItem() == null
    || cbbNamHoc.getSelectedItem().equals("Không có niên khóa")
    || cbbNamHoc.getSelectedItem().equals("Chọn niên khóa")) {
    JOptionPane.showMessageDialog(this,
        "Vui lòng chọn một năm học hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    return;
}
String moTaNamHoc = cbbNamHoc.getSelectedItem().toString();
    String diem45Str = TxtBL.getText().trim();
    String diemGiuaKyStr = TxtGiuaKy.getText().trim();
    String diemCuoiKyStr = TxtCuoiKy.getText().trim();
    String regex = "^(10(\\.0)?|[0-9](\\.\\d{0,1})?)$";

    if (diem45Str.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Điểm 45 phút không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (diemGiuaKyStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Điểm giữa kỳ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (diemCuoiKyStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Điểm cuối kỳ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!diem45Str.matches(regex)) {
        JOptionPane.showMessageDialog(this, "Điểm 45 phút phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (!diemGiuaKyStr.matches(regex)) {
        JOptionPane.showMessageDialog(this, "Điểm giữa kỳ phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (!diemCuoiKyStr.matches(regex)) {
        JOptionPane.showMessageDialog(this, "Điểm cuối kỳ phải là số thực từ 0 đến 10.0 (ví dụ: 7, 8.5, 10.0)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        float diem45 = Float.parseFloat(diem45Str);
        float diemGiuaKy = Float.parseFloat(diemGiuaKyStr);
        float diemCuoiKy = Float.parseFloat(diemCuoiKyStr);

        if (diem45 < 0.0 || diem45 > 10.0) {
            JOptionPane.showMessageDialog(this, "Điểm 45 phút phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (diemGiuaKy < 0.0 || diemGiuaKy > 10.0) {
            JOptionPane.showMessageDialog(this, "Điểm giữa kỳ phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (diemCuoiKy < 0.0 || diemCuoiKy > 10.0) {
            JOptionPane.showMessageDialog(this, "Điểm cuối kỳ phải nằm trong khoảng từ 0.0 đến 10.0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maMonHoc = monHocList.get(CbbMonHoc.getSelectedIndex() - 1).getMaMonHoc();
        String maHocKy = CbbHocKy.getSelectedItem().toString();

        // Kiểm tra maHocKy có hợp lệ
        boolean validHocKy = false;
        for (HocKy hocKy : hocKyList) {
            if (hocKy.getMaHocKy().equals(maHocKy)) {
                validHocKy = true;
                break;
            }
        }
        if (!validHocKy) {
            JOptionPane.showMessageDialog(this, "Học kỳ không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thêm điểm cho học sinh với mã " + maHocSinh + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = controller.addDiem(maHocSinh, maMonHoc, maHocKy, moTaNamHoc, diem45, diemGiuaKy, diemCuoiKy);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            fillToTable1();

            // Cập nhật lại CbbMonHoc với tất cả môn học
            CbbMonHoc.removeAllItems();
            CbbMonHoc.addItem("Chọn môn học");
            monHocList = controller.loadMonHoc();
            for (MonHoc monHoc : monHocList) {
                CbbMonHoc.addItem(monHoc.getTenMonHoc());
            }
            CbbMonHoc.setSelectedIndex(0);

            // Reset CbbHocKy
            CbbHocKy.removeAllItems();
            CbbHocKy.addItem("Chọn học kỳ");
            CbbHocKy.setSelectedIndex(0);

            // Reset các trường nhập liệu
            TxtMa.setText("");
            TxtBL.setText("");
            TxtGiuaKy.setText("");
            TxtCuoiKy.setText("");
            CbbTen.setSelectedIndex(0);
            CbbNamHoc.removeAllItems();
            CbbNamHoc.addItem("Chọn năm học");
            namHocList = controller.loadNamHoc();
            if (namHocList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có năm học nào trong cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                CbbNamHoc.setEnabled(false);
            } else {
                for (NamHoc namHoc : namHocList) {
                    CbbNamHoc.addItem(namHoc.getMoTa());
                }
                CbbNamHoc.setEnabled(true);
            }
            CbbNamHoc.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm điểm thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ (số thực)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
int selectedRow = jTable2.getSelectedRow();
    if (selectedRow >= 0) {
        // Lấy giá trị từ các cột
        String tenHocSinh = (String) tableModel2.getValueAt(selectedRow, 0);
        String maHocSinh = (String) tableModel2.getValueAt(selectedRow, 1);
        String hanhKiem = (String) tableModel2.getValueAt(selectedRow, 4);
        String ghiChu = (String) tableModel2.getValueAt(selectedRow, 5);

        // Cập nhật các trường
        txtTenHS.setText(tenHocSinh);
        selectedMaHocSinh = maHocSinh;
        jTextArea1.setText(ghiChu != null ? ghiChu : "");

        // Cập nhật CbbHanhKiem với giá trị hạnh kiểm
        if (hanhKiem != null && !hanhKiem.equals("N/A")) {
            CbbHanhKiem.setSelectedItem(hanhKiem);
        } else {
            CbbHanhKiem.setSelectedIndex(-1);
        }

        // Cập nhật cbbHocKy với danh sách mã học kỳ
        cbbHocKy.removeAllItems();
        cbbHocKy.addItem("Chọn học kỳ");
        List<String> usedHocKyList = controller.getUsedHocKyForHocSinh(maHocSinh);
        if (usedHocKyList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có học kỳ nào cho học sinh này!", 
                                         "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            for (String maHocKy : usedHocKyList) {
                cbbHocKy.addItem(maHocKy);
            }
        }
        cbbHocKy.setSelectedIndex(0);
    }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
   if (selectedMaHocSinh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một học sinh từ bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        jLabel25.setText(txtTenHS.getText());
        fillToTable4(selectedMaHocSinh);
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//    String maHocSinh = txtTenHS.getText().trim();
//    if (maHocSinh.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//    String hanhKiem = CbbHanhKiem.getSelectedItem() != null ? CbbHanhKiem.getSelectedItem().toString() : "";
//    String ghiChu = jTextArea1.getText().trim();
//    String maHocKy = cbbHocKy.getSelectedItem() != null && !cbbHocKy.getSelectedItem().equals("Chọn học kỳ") ? cbbHocKy.getSelectedItem().toString() : "";
//
//    if (hanhKiem.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng chọn hạnh kiểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//    if (maHocKy.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng chọn học kỳ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        return;
//    }
//
//    // Xác nhận trước khi cập nhật
//    int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận cập nhật hạnh kiểm cho học sinh với mã " + maHocSinh + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//    if (confirm != JOptionPane.YES_OPTION) {
//        return;
//    }
//
//    boolean success = controller.updateHanhKiem(maHocSinh, hanhKiem, ghiChu, maHocKy);
//    if (success) {
//        JOptionPane.showMessageDialog(this, "Cập nhật hạnh kiểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//        fillToTable2();
//        // Làm mới các trường nhập liệu
//        txtTenHS.setText("");
//        CbbHanhKiem.setSelectedIndex(-1);
//        jTextArea1.setText("");
//        selectedMaHocSinh = null;
//    } else {
//        JOptionPane.showMessageDialog(this, "Cập nhật thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//    }
    
    // Kiểm tra xem học sinh đã được chọn từ jTable2 chưa
 // Kiểm tra xem học sinh đã được chọn từ jTable2 chưa
    // Kiểm tra xem học sinh đã được chọn từ jTable2 chưa
        if (selectedMaHocSinh == null || selectedMaHocSinh.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một học sinh từ bảng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra MaHocSinh có tồn tại trong bảng HocSinh
        if (!controller.isHocSinhExists(selectedMaHocSinh)) {
            JOptionPane.showMessageDialog(this, "Mã học sinh " + selectedMaHocSinh + " không tồn tại trong cơ sở dữ liệu!", 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy giá trị từ các trường
        String hanhKiem = CbbHanhKiem.getSelectedItem() != null ? CbbHanhKiem.getSelectedItem().toString() : "";
        String ghiChu = jTextArea1.getText().trim();
        String maHocKy = cbbHocKy.getSelectedItem() != null && !cbbHocKy.getSelectedItem().equals("Chọn học kỳ") ? 
                         cbbHocKy.getSelectedItem().toString() : "";

        // Kiểm tra dữ liệu đầu vào
        if (hanhKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hạnh kiểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (maHocKy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học kỳ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác nhận trước khi lưu
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Xác nhận cập nhật hạnh kiểm cho học sinh với mã " + selectedMaHocSinh + "?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Thực hiện thêm hoặc cập nhật hạnh kiểm
        boolean success = controller.updateHanhKiem(selectedMaHocSinh, hanhKiem, ghiChu, maHocKy);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật hạnh kiểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Làm mới bảng jTable2
            fillToTable2();
            // Xóa các trường nhập liệu
            txtTenHS.setText("");
            CbbHanhKiem.setSelectedIndex(-1);
            jTextArea1.setText("");
            cbbHocKy.setSelectedIndex(0);
            selectedMaHocSinh = null;
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void CbbNamHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbNamHocActionPerformed
//String moTaNamHoc = CbbNamHoc.getSelectedItem() != null && !CbbNamHoc.getSelectedItem().equals("Chọn năm học") 
//                           ? CbbNamHoc.getSelectedItem().toString() 
//                           : "";
//        if (moTaNamHoc.isEmpty()) {
//            Cbblop.setEnabled(false);
//            Cbblop.removeAllItems();
//            Cbblop.addItem("Chọn lớp");
//            tableModel2.setRowCount(0);
//            lopList.clear(); // Xóa danh sách lopList khi không có năm học
//        } else {
//            Cbblop.setEnabled(true);
//            Cbblop.removeAllItems();
//            Cbblop.addItem("Chọn lớp");
//            List<Lop> lopHocList = controller.getLopHocByNamHoc(moTaNamHoc);
//            lopList.clear(); // Xóa danh sách cũ trước khi gán mới
//            lopList.addAll(lopHocList); // Gán danh sách lớp mới vào lopList
//            for (Lop lop : lopList) {
//                Cbblop.addItem(lop.getTenLop());
//            }
//        }

 String selectedNamHoc = CbbNamHoc.getSelectedItem() != null ? CbbNamHoc.getSelectedItem().toString() : null;
    if (selectedNamHoc != null && !selectedNamHoc.equals("Chọn năm học")) {
        updateCbbHk();
        cbbHk.setEnabled(true);
    } else {
        cbbHk.removeAllItems();
        cbbHk.addItem("Chọn học kỳ");
        cbbHk.setEnabled(false);
        updateLopHocComboBox(null, null);
    }

    }//GEN-LAST:event_CbbNamHocActionPerformed

    private void CbbTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbTenActionPerformed
String selectedTen = (String) CbbTen.getSelectedItem();
    if (selectedTen != null && !selectedTen.equals("Chọn học sinh")) {
        for (HocSinh hocSinh : hocSinhList) {
            if (hocSinh.getTenHocSinh() != null && hocSinh.getTenHocSinh().equals(selectedTen)) {
                String maHocSinh = hocSinh.getMaHocSinh() != null ? hocSinh.getMaHocSinh() : "";
                String maLop = controller.getMaLopByMaHocSinh(maHocSinh);
                TxtMa.setText(maHocSinh);
                txtMaLop.setText(maLop != null ? maLop : "N/A");
                updateCbbNamHoc(maHocSinh);

                // Cập nhật CbbMonHoc với tất cả môn học
                CbbMonHoc.removeAllItems();
                CbbMonHoc.addItem("Chọn môn học");
                monHocList = controller.loadMonHoc(); // Lấy tất cả môn học
                for (MonHoc monHoc : monHocList) {
                    CbbMonHoc.addItem(monHoc.getTenMonHoc());
                }
                CbbMonHoc.setSelectedIndex(0);

                // Reset CbbHocKy
                CbbHocKy.removeAllItems();
                CbbHocKy.addItem("Chọn học kỳ");
                CbbHocKy.setSelectedIndex(0);

                System.out.println("Đã chọn học sinh: " + selectedTen + ", MaLop: " + maLop);
                break;
            }
        }
    } else {
        TxtMa.setText("");
        txtMaLop.setText("");
        updateCbbNamHoc(null);
        CbbMonHoc.removeAllItems();
        CbbMonHoc.addItem("Chọn môn học");
        CbbHocKy.removeAllItems();
        CbbHocKy.addItem("Chọn học kỳ");
        CbbHocKy.setSelectedIndex(0);
    }
    }//GEN-LAST:event_CbbTenActionPerformed

    private void cbbHkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHkActionPerformed
String selectedNamHoc = CbbNamHoc.getSelectedItem() != null ? CbbNamHoc.getSelectedItem().toString() : null;
    String maHocKy = cbbHk.getSelectedItem() != null ? cbbHk.getSelectedItem().toString() : null;
    if (selectedNamHoc != null && !selectedNamHoc.equals("Chọn năm học") && maHocKy != null && !maHocKy.equals("Chọn học kỳ")) {
        updateLopHocComboBox(selectedNamHoc, maHocKy);
    } else {
        updateLopHocComboBox(null, null);
    }
    }//GEN-LAST:event_cbbHkActionPerformed

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed

    }//GEN-LAST:event_btnInActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbbHanhKiem;
    private javax.swing.JComboBox<String> CbbHocKy;
    private javax.swing.JComboBox<String> CbbMonHoc;
    private javax.swing.JComboBox<String> CbbNamHoc;
    private javax.swing.JComboBox<String> CbbTen;
    private javax.swing.JComboBox<String> Cbblop;
    private javax.swing.JTextField TxtBL;
    private javax.swing.JTextField TxtCuoiKy;
    private javax.swing.JTextField TxtGiuaKy;
    private javax.swing.JTextField TxtMa;
    private javax.swing.JButton btnIn;
    private javax.swing.JComboBox<String> cbbHk;
    private javax.swing.JComboBox<String> cbbHocKy;
    private javax.swing.JComboBox<String> cbbNamHoc;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelThieuDiem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtMaLop;
    private javax.swing.JTextField txtTenHS;
    // End of variables declaration//GEN-END:variables
}
