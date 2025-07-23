
package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
     private static String dbUrl = "jdbc:sqlserver://14.225.205.75:1433;"
            + "Database=QLTruongHoc2;trustServerCertificate=true";
    private static String Username = "sa";
    private static String Password = "Abcd1234@";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(dbUrl, Username, Password);
//            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException e) {
//            System.err.println("Không tìm thấy driver SQL Server: " + e.getMessage());
        } catch (SQLException e) {
//            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return connection; // Trả về connection để sử dụng
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
//                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
//                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
