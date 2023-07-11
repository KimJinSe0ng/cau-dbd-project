package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Programming{
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gallery?useUnicode=true&useJDBCCompliantTimezoneShift=true&" + "useLegacyDatetimeCode=false&serverTimezone=UTC", "ID", "PW");
            // Select
            Statement stmt= conn.createStatement();
            ResultSet rset= stmt.executeQuery("select * from artwork");
            while (rset.next()) {
                System.out.println("Work_number "+ rset.getString("work_number") + ", work_name = "+ rset.getString("work_name") + ", artist = "+ rset.getString("artist") + ", production_year = "+ rset.getString("production_year") + ", genre = "+ rset.getString("genre"));
            }

            // Insert
            String sql= "insert into artwork values (?,?,?,?,?)";
            PreparedStatement pstmt= conn.prepareStatement(sql);
            pstmt.setString(1, "500");
            pstmt.setString(2, "Guernica");
            pstmt.setString(3, "Pablo Picasso");
            pstmt.setString(4, "1937");
            pstmt.setString(5, "유화");
            pstmt.executeUpdate();
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            // Select
            stmt= conn.createStatement();
            rset= stmt.executeQuery("select * from artwork");
            while (rset.next()) {
                System.out.println("Work_number "+ rset.getString("work_number") + ", work_name = "+ rset.getString("work_name") + ", artist = "+ rset.getString("artist") + ", production_year = "+ rset.getString("production_year") + ", genre = "+ rset.getString("genre"));
            }
            stmt.close();
            conn.close();
        }
        catch(SQLException sqle) {
            System.out.println("SQLException: "+sqle);
        }
    }
}
