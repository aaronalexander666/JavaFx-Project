import java.sql.Connection;
import java.sql.DriverManager;
public class TestDBConnection {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafxdemo?useSSL=false", "root", "");
        System.out.println("Connection successful!");
        conn.close();
    }
}
