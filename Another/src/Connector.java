import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Connector {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/example?useSSL=FALSE&allowPublicKeyRetrieval=TRUE";
    static final String USER = "root";
    static final String PASS = "1234";

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Registration failed");

        }

        try {
            // Establish Connection
            System.out.println("Connecting Database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Initialize Statement
            System.out.println("Instancize Statement...\n");
            stmt = conn.createStatement();

            // Create table TESTNEW
            String sql = "CREATE TABLE IF NOT EXISTS TESTNEW(id INTEGER PRIMARY KEY AUTO_INCREMENT,username varchar(24) NOT NULL, age INTEGER NOT NULL,";
            sql += "update_time timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP)";
            System.out.println(sql + '\n');
            stmt.executeUpdate(sql);

            // Create trigger to update time
            sql = "CREATE TRIGGER IF NOT EXISTS updateTrigger BEFORE UPDATE ON testnew FOR EACH ROW BEGIN SET NEW.update_time=NOW(); END";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');

            // Insert Data
            sql = "INSERT INTO TESTNEW (username,age) VALUES ('Tom', 18)";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');
            sql = "INSERT INTO TESTNEW (username,age) VALUES ('Jessica', 36)";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');
            sql = "INSERT INTO TESTNEW (username,age) VALUES ('Jack', 20)";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');
            sql = "INSERT INTO TESTNEW (username,age) VALUES ('Catherine', 10)";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');

            // Update Data
            sql = "UPDATE testnew SET username = 'Austin' where id = 1";
            stmt.executeUpdate(sql);
            System.out.println(sql + '\n');

            // Query
            sql = "SELECT * FROM testnew where age < 20";
            ResultSet result = stmt.executeQuery(sql);
            System.out.println(sql + '\n');

            while (result.next()) {
                System.out.println("id: " + result.getInt("id"));
                System.out.println("username: " + result.getString("username"));
                System.out.println("age: " + result.getInt("age"));
                System.out.println("update_time: " + result.getTimestamp("update_time") + '\n');
            }

            result.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
    }
}
