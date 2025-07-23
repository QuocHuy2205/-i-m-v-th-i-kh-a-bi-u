/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package QuanLiThoiKhoaBieu;

import DAO.thoiKhoaBieuDAO;
import Model.HocKy;
import Model.MonHoc;
import Model.ThoiKhoaBieu;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.util.Calendar;

public class QuanLiThoiKhoaBieu extends javax.swing.JPanel {
    private DefaultTableModel tableModel1;
    private DefaultTableModel tableModel2;
    private boolean isDatePickerVisible = false;
    private JDateChooser currentDateChooser = null;
    private List<MonHoc> monHocList;
    private final thoiKhoaBieuDAO tkb;
    public QuanLiThoiKhoaBieu() {
        tkb = new thoiKhoaBieuDAO();
        monHocList = new ArrayList<>();
        initComponents();
        setupDateField();
        initTableModels();
        loadLopHoc();
        loadGiaoVien();
        loadMonHoc();
        loadPhongHoc();
        loadHocKy();
        loadNamHoc();
        loadLopHocHoc();
        loadTableData();
          loadTableData2();
        cbbLopHoc.addActionListener(e -> filterTable2());
jDate.addPropertyChangeListener("date", evt -> filterTable2());
      
         // Thêm PropertyChangeListener cho currentDateChooser để kiểm tra ngày ngay khi chọn
        currentDateChooser.addPropertyChangeListener("date", evt -> {
            validateSelectedDate();
        });
    }
    
    private void initTableModels() {
  tableModel1 = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Lớp", "Giáo viên", "Môn học", "Phòng", "Ngày", "Tiết", "Học kỳ", "Mã TKB", "Ghi chú"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel1);
        
        tableModel2 = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Lớp", "Giáo viên", "Môn học", "Phòng", "Ngày", "Tiết", "Học kỳ", "Mã TKB", "Ghi chú"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };  
        jTable2.setModel(tableModel2);
    }
    
private void loadTableData() {
        tableModel1.setRowCount(0);
        List<ThoiKhoaBieu> tkbList = tkb.getAllThoiKhoaBieu();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ThoiKhoaBieu tkb : tkbList) {
            tableModel1.addRow(new Object[]{
                tkb.getTenLop(),
                tkb.getTenNguoiDung(),
                tkb.getTenMonHoc(),
                tkb.getMaPhongHoc(),
                tkb.getNgay() != null ? sdf.format(tkb.getNgay()) : "",
                tkb.getTiet(),
                tkb.getTenHocKy(),
                tkb.getMaTKB(),
                tkb.getGhichu() != null ? tkb.getGhichu() : ""
            });
        }
    }

    private void loadTableData2() {
        tableModel2.setRowCount(0);
        List<ThoiKhoaBieu> tkbList = tkb.getAllThoiKhoaBieu();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ThoiKhoaBieu tkb : tkbList) {
            tableModel2.addRow(new Object[]{
                tkb.getTenLop(),
                tkb.getTenNguoiDung(),
                tkb.getTenMonHoc(),
                tkb.getMaPhongHoc(),
                tkb.getNgay() != null ? sdf.format(tkb.getNgay()) : "",
                tkb.getTiet(),
                tkb.getTenHocKy(),
                tkb.getMaTKB(),
                tkb.getGhichu() != null ? tkb.getGhichu() : ""
            });
        }
    }
    
    private void loadMonHoc() {
        cbbMon.removeAllItems();
        cbbMon.addItem("Chọn môn học");
        monHocList = tkb.getAllMonHoc();
        for (MonHoc monHoc : monHocList) {
            cbbMon.addItem(monHoc.getTenMonHoc());
        }
        cbbMon.setSelectedIndex(0);
    }
    
    private void loadNamHoc() {
        cbbNamHoc.removeAllItems();
        cbbNamHoc.addItem("Chọn niên khóa");
        List<String> nienKhoaList = tkb.getAllNienKhoa();
        for (String nienKhoa : nienKhoaList) {
            cbbNamHoc.addItem(nienKhoa);
        }
        cbbNamHoc.setSelectedIndex(0);
    }
    
private void loadLopHoc() {
        cbbLop.removeAllItems();
        cbbLop.addItem("Chọn lớp");
        String selectedNienKhoa = (String) cbbNamHoc.getSelectedItem();
        if (selectedNienKhoa != null && !selectedNienKhoa.equals("Chọn niên khóa")) {
            List<String> lopHocList = tkb.getLopHocByNienKhoa(selectedNienKhoa);
            for (String tenLop : lopHocList) {
                cbbLop.addItem(tenLop);
            }
        } else {
            List<String> lopHocList = tkb.getAllLopHoc();
            for (String tenLop : lopHocList) {
                cbbLop.addItem(tenLop);
            }
        }
        cbbLop.setSelectedIndex(0);
        
    }

private void loadLopHocHoc() {
    cbbLopHoc.removeAllItems();
    cbbLopHoc.addItem("Chọn lớp");
    // Lấy danh sách tên lớp từ DAO
    List<String> lopHocList = tkb.getAllLopHoc();
    for (String lop : lopHocList) {
        cbbLopHoc.addItem(lop);
    }
    cbbLopHoc.setSelectedIndex(0);
}

    
    private void loadGiaoVien() {
        cbbGV.removeAllItems();
        cbbGV.addItem("Chọn giáo viên");
        List<String> giaoVienList = tkb.getAllGiaoVien();
        for (String tenGiaoVien : giaoVienList) {
            cbbGV.addItem(tenGiaoVien);
        }
        cbbGV.setSelectedIndex(0);
    }
    
    private void loadHocKy() {
        cbbHocKy.removeAllItems();
        cbbHocKy.addItem("Chọn học kỳ");
        List<String> hocKyList = tkb.getAllHocKy();
        for (String tenHocKy : hocKyList) {
            cbbHocKy.addItem(tenHocKy);
        }
        cbbHocKy.setSelectedIndex(0);
    }
    
    private void loadPhongHoc() {
        cbbPhong.removeAllItems();
        cbbPhong.addItem("Chọn phòng học");
        List<String> phongHocList = tkb.getAllMaPhongHoc();
        for (String maPhong : phongHocList) {
            cbbPhong.addItem(maPhong);
        }
        cbbPhong.setSelectedIndex(0);
    }
    
    private void setupDateField() {
        jTextField7.setEditable(false);
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            dateFormatter.setAllowsInvalid(false);
            
            JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
            dateField.setFont(new java.awt.Font("Segoe UI", 0, 14));
            dateField.setText("");
            dateField.setToolTipText("Nhấp đúp để chọn ngày hoặc nhập trực tiếp (dd/MM/yyyy)");
            
            jPanel2.remove(jTextField7);
            jPanel2.add(dateField, new AbsoluteConstraints(28, 295, 160, -1));
            jTextField7 = dateField;
            
            showDatePicker();
            
            jPanel2.revalidate();
            jPanel2.repaint();
            
        } catch (ParseException e) {
            System.err.println("Error creating date formatter: " + e.getMessage());
            setupBasicDateField();
        }
    }
    
    private void setupBasicDateField() {
        jTextField7.setText("");
        jTextField7.setToolTipText("Nhập ngày theo định dạng dd/MM/yyyy");
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                validateDateFormat();
            }
        });
    }
    
    private void showDatePicker() {
        if (isDatePickerVisible) return;
        
        try {
            isDatePickerVisible = true;
            
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");
            dateChooser.setFont(new java.awt.Font("Segoe UI", 0, 14));
            
            Date currentDate = getCurrentDateFromField();
            dateChooser.setDate(currentDate != null ? currentDate : new Date());
            
            jTextField7.setVisible(false);
            jPanel2.add(dateChooser, new AbsoluteConstraints(28, 295, 160, -1));
            currentDateChooser = dateChooser;
            
            dateChooser.addPropertyChangeListener("date", evt -> {
                Date selectedDate = dateChooser.getDate();
                if (selectedDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    jTextField7.setText(sdf.format(selectedDate));
                }
            });
            
            jPanel2.revalidate();
            jPanel2.repaint();
            
            javax.swing.SwingUtilities.invokeLater(() -> {
                dateChooser.requestFocus();
            });
            
        } catch (Exception e) {
            System.err.println("Error showing date picker: " + e.getMessage());
            isDatePickerVisible = false;
        }
    }
    
    private Date getCurrentDateFromField() {
        try {
            String currentText = jTextField7.getText();
            if (currentText != null && !currentText.trim().isEmpty() && 
                !currentText.equals("mm/dd/yyyy") && !currentText.equals("__/__/____")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                return sdf.parse(currentText);
            }
        } catch (ParseException e) {
            System.out.println("Could not parse current date: " + jTextField7.getText());
        }
        return null;
    }
    
    private void validateDateFormat() {
        String text = jTextField7.getText().trim();
        if (!text.isEmpty() && !text.equals("__/__/____")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Date date = sdf.parse(text);
                jTextField7.setText(sdf.format(date));
            } catch (ParseException e) {
                showError("Định dạng ngày không hợp lệ!\nVui lòng nhập theo định dạng: dd/MM/yyyy\nVí dụ: 16/07/2025");
                jTextField7.requestFocus();
            }
        }
    }
    
    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(
            this, message, "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    
    // Public methods for external use
    public Date getSelectedDate() {
        return getCurrentDateFromField();
    }
    
    public void setSelectedDate(Date date) {
    if (currentDateChooser != null) {
        currentDateChooser.setDate(date);
    }
    }
// Kiểm tra ngày hợp lệ với niên khóa
    private boolean isDateInNienKhoa(Date selectedDate, String nienKhoa) {
        if (nienKhoa == null || "Chọn niên khóa".equals(nienKhoa)) {
            return true; // Chưa chọn niên khóa, không kiểm tra
        }

        // Trích xuất năm từ niên khóa (ví dụ: "2022-2023" -> 2022 và 2023)
        String[] years = nienKhoa.split("-");
        if (years.length != 2) {
            return false; // Niên khóa không đúng định dạng
        }

        try {
            int startYear = Integer.parseInt(years[0]);
            int endYear = Integer.parseInt(years[1]);

            // Lấy năm từ ngày được chọn
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            int selectedYear = calendar.get(Calendar.YEAR);

            // Kiểm tra năm của ngày có thuộc niên khóa không
            return selectedYear >= startYear && selectedYear <= endYear;
        } catch (NumberFormatException e) {
            System.err.println("Lỗi khi phân tích niên khóa: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra ngày khi chọn từ currentDateChooser
private void validateSelectedDate() {
    String nienKhoa = (String) cbbNamHoc.getSelectedItem();
    Date selectedDate = currentDateChooser.getDate();

    if (selectedDate == null || nienKhoa == null || "Chọn niên khóa".equals(nienKhoa)) {
        return; // Không kiểm tra nếu chưa chọn ngày hoặc niên khóa
    }

    // Trích xuất năm từ niên khóa (ví dụ: "2022-2023" -> 2022 và 2023)
    String[] years = nienKhoa.split("-");
    if (years.length != 2) {
        JOptionPane.showMessageDialog(this, 
            "Niên khóa không đúng định dạng!", 
            "Lỗi niên khóa", 
            JOptionPane.ERROR_MESSAGE);
        currentDateChooser.setDate(null);
        return;
    }

    try {
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        // Tạo ngày bắt đầu (3/9 của năm đầu) và ngày kết thúc (30/5 của năm sau)
        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, Calendar.SEPTEMBER, 3, 0, 0, 0);
        startDate.set(Calendar.MILLISECOND, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, Calendar.MAY, 30, 23, 59, 59);
        endDate.set(Calendar.MILLISECOND, 999);

        // Lấy ngày được chọn
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);
        selectedCal.set(Calendar.HOUR_OF_DAY, 0);
        selectedCal.set(Calendar.MINUTE, 0);
        selectedCal.set(Calendar.SECOND, 0);
        selectedCal.set(Calendar.MILLISECOND, 0);

        // Kiểm tra ngày có nằm trong khoảng hợp lệ không
        if (selectedCal.before(startDate) || selectedCal.after(endDate)) {
            JOptionPane.showMessageDialog(this, 
                "Ngày được chọn không thuộc niên khóa " + nienKhoa + "!\n" +
                "Vui lòng chọn ngày từ 03/09/" + startYear + " đến 30/05/" + endYear, 
                "Lỗi ngày", 
                JOptionPane.ERROR_MESSAGE);
            currentDateChooser.setDate(null); // Xóa ngày không hợp lệ
            jTextField7.setText(""); // Xóa text trong jTextField7
        }
    } catch (NumberFormatException e) {
        System.err.println("Lỗi khi phân tích niên khóa: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Niên khóa không hợp lệ!", 
            "Lỗi niên khóa", 
            JOptionPane.ERROR_MESSAGE);
        currentDateChooser.setDate(null);
        jTextField7.setText("");
    }
}
    
    private void filterTable2() {
    String tenLop = (String) cbbLopHoc.getSelectedItem();
    Date selectedDate = jDate.getDate();        // jDate là JDateChooser của bạn

    // Nếu chưa chọn lớp hoặc ngày thì không làm gì
    if (tenLop == null || tenLop.equals("Chọn lớp") || selectedDate == null) {
        return;
    }

    // Xóa hết dữ liệu cũ
    tableModel2.setRowCount(0);

    // Format cho giống với dữ liệu trong DB
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String ngayStr = sdf.format(selectedDate);

    // Lấy tất cả, rồi lọc
    List<ThoiKhoaBieu> all = tkb.getAllThoiKhoaBieu();
    for (ThoiKhoaBieu tkbEntry : all) {
        String entryLop = tkbEntry.getTenLop();
        String entryNgay = tkbEntry.getNgay() != null 
            ? sdf.format(tkbEntry.getNgay()) 
            : "";
        if (tenLop.equals(entryLop) && ngayStr.equals(entryNgay)) {
            tableModel2.addRow(new Object[]{
                tkbEntry.getTenLop(),
                tkbEntry.getTenNguoiDung(),
                tkbEntry.getTenMonHoc(),
                tkbEntry.getMaPhongHoc(),
                entryNgay,
                tkbEntry.getTiet(),
                tkbEntry.getTenHocKy(),
                tkbEntry.getMaTKB()
            });
        }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbbLop = new javax.swing.JComboBox<>();
        cbbGV = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        cbbPhong = new javax.swing.JComboBox<>();
        cbbMon = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        cbbHocKy = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbbNamHoc = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cbbLopHoc = new javax.swing.JComboBox<>();
        ngày = new javax.swing.JLabel();
        jDate = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Phân công các tiết học vào thời khóa biểu.");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 20, -1, -1));

        jLabel7.setText("Chọn Lớp:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 54, -1, -1));

        jLabel8.setText("Chọn Giáo viên:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 54, -1, -1));

        jLabel9.setText("Môn học:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 152, -1, -1));

        jLabel10.setText("Phòng học:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 152, -1, -1));

        jLabel11.setText("Ngày:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 261, -1, -1));

        jLabel12.setText("Tiết:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 261, -1, -1));

        cbbLop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbbLop, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 210, -1));

        cbbGV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbbGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 76, 210, -1));
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 295, 150, -1));

        jButton1.setText("phân công tiết");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 114, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel14.setText("Thời khóa biểu đã phân công");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 424, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Lớp", "Giáo viên", "Môn", "Phòng", "Ngày", "Tiết", "Buổi"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 455, 730, 156));
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 160, -1));

        cbbPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbbPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, 210, -1));

        cbbMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMonActionPerformed(evt);
            }
        });
        jPanel2.add(cbbMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 210, -1));

        jLabel1.setText("Ghi chú:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, 250, 100));

        jLabel2.setText("Học kỳ:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, -1, -1));

        cbbHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbbHocKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 180, -1));

        jLabel3.setText("Năm học:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        cbbNamHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbNamHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNamHocActionPerformed(evt);
            }
        });
        jPanel2.add(cbbNamHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 200, -1));

        jTabbedPane1.addTab("Phân công Tiết dạy", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable2);

        jLabel4.setText("Lớp:");

        cbbLopHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbLopHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLopHocActionPerformed(evt);
            }
        });

        ngày.setText("Ngày:");

        jButton2.setText("làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ngày)
                            .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(123, 123, 123)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 389, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLopHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ngày)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbLopHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2))
                    .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thời khóa biểu", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
String tenLop = (String) cbbLop.getSelectedItem();
    String tenNguoiDung = (String) cbbGV.getSelectedItem();
    String tenMonHoc = (String) cbbMon.getSelectedItem();
    String tiet = jTextField3.getText().trim();
    String maPhongHoc = (String) cbbPhong.getSelectedItem();
    String tenHocKy = (String) cbbHocKy.getSelectedItem();
    String ghichu = jTextArea1.getText().trim();
    String nienKhoa = (String) cbbNamHoc.getSelectedItem();

    // Kiểm tra thông tin đầu vào
    if ("Chọn lớp".equals(tenLop) || "Chọn giáo viên".equals(tenNguoiDung) || 
        "Chọn môn học".equals(tenMonHoc) || tiet.isEmpty() || 
        "Chọn phòng học".equals(maPhongHoc) || "Chọn học kỳ".equals(tenHocKy) ||
        "Chọn niên khóa".equals(nienKhoa)) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
        return;
    }

    // Kiểm tra số tiết hợp lệ
    try {
        int tietNum = Integer.parseInt(tiet);
        if (tietNum < 1 || tietNum > 10) {
            JOptionPane.showMessageDialog(this, "Số tiết phải từ 1 đến 10!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số tiết phải là số nguyên hợp lệ!");
        return;
    }

    // Kiểm tra ghi chú không chứa ký tự đặc biệt
    if (!ghichu.matches("^[a-zA-Z0-9\\s\\.,]*$")) {
        JOptionPane.showMessageDialog(this, "Ghi chú chỉ được chứa chữ cái, số, khoảng trắng, dấu chấm và dấu phẩy!");
        return;
    }

    // Lấy ngày từ JDateChooser
    Date ngay = currentDateChooser != null ? currentDateChooser.getDate() : null;
    if (ngay == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngày hợp lệ!");
        return;
    }

    // Kiểm tra ngày có thuộc niên khóa không
    String[] years = nienKhoa.split("-");
    if (years.length != 2) {
        JOptionPane.showMessageDialog(this, "Niên khóa không đúng định dạng!", "Lỗi niên khóa", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, Calendar.SEPTEMBER, 3, 0, 0, 0);
        startDate.set(Calendar.MILLISECOND, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, Calendar.MAY, 30, 23, 59, 59);
        endDate.set(Calendar.MILLISECOND, 999);

        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(ngay);
        selectedCal.set(Calendar.HOUR_OF_DAY, 0);
        selectedCal.set(Calendar.MINUTE, 0);
        selectedCal.set(Calendar.SECOND, 0);
        selectedCal.set(Calendar.MILLISECOND, 0);

        if (selectedCal.before(startDate) || selectedCal.after(endDate)) {
            JOptionPane.showMessageDialog(this, 
                "Ngày được chọn không thuộc niên khóa " + nienKhoa + "!\n" +
                "Vui lòng chọn ngày từ 03/09/" + startYear + " đến 30/05/" + endYear, 
                "Lỗi ngày", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Niên khóa không hợp lệ!", "Lỗi niên khóa", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra ngày không được trong quá khứ
    Date today = new Date();
    if (ngay.before(today)) {
        JOptionPane.showMessageDialog(this, "Ngày chọn không thể là ngày trong quá khứ!");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String ngayStr = sdf.format(ngay);

    thoiKhoaBieuDAO dao = new thoiKhoaBieuDAO();
    // Kiểm tra trùng lịch giáo viên
    if (dao.isTeacherScheduleConflict(tenNguoiDung, ngayStr, tiet)) {
        JOptionPane.showMessageDialog(this, "Giáo viên " + tenNguoiDung + " đã được phân công dạy tiết " + tiet + " vào ngày " + ngayStr + "!");
        return;
    }

    String maTKB = dao.generateNewMaTKB();

    boolean success = dao.saveThoiKhoaBieu(maTKB, tenLop, tenNguoiDung, tenMonHoc, ngayStr, tiet, maPhongHoc, tenHocKy, ghichu);

    if (success) {
        JOptionPane.showMessageDialog(this, "Phân công tiết học thành công! Mã TKB: " + maTKB);
        loadTableData();
        loadTableData2();
    } else {
        JOptionPane.showMessageDialog(this, "Lỗi khi phân công tiết học!");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMonActionPerformed
        String selectedMonHoc = (String) cbbMon.getSelectedItem();
                if (selectedMonHoc != null && !selectedMonHoc.equals("Chọn môn học")) {
                    List<String> giaoVienList = tkb.getGiaoVienByMonHoc(selectedMonHoc);
                    cbbGV.removeAllItems();
                    cbbGV.addItem("Chọn giáo viên");
                    for (String tenGiaoVien : giaoVienList) {
                        cbbGV.addItem(tenGiaoVien);
                    }
                    cbbGV.setSelectedIndex(0);
                } else {
                    loadGiaoVien();
                }
    }//GEN-LAST:event_cbbMonActionPerformed

    private void cbbNamHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNamHocActionPerformed
            loadLopHoc();
    }//GEN-LAST:event_cbbNamHocActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cbbLopHoc.setSelectedIndex(0);    // chọn lại “Chọn lớp”
    jDate.setDate(null);              // bỏ chọn ngày
    
    // 2. Xóa hết dữ liệu cũ
    tableModel2.setRowCount(0);
    
    // 3. Nạp lại toàn bộ dữ liệu lên table (như loadTableData2 ban đầu)
    loadTableData2();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbbLopHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLopHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbLopHocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbGV;
    private javax.swing.JComboBox<String> cbbHocKy;
    private javax.swing.JComboBox<String> cbbLop;
    private javax.swing.JComboBox<String> cbbLopHoc;
    private javax.swing.JComboBox<String> cbbMon;
    private javax.swing.JComboBox<String> cbbNamHoc;
    private javax.swing.JComboBox<String> cbbPhong;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel ngày;
    // End of variables declaration//GEN-END:variables
}
