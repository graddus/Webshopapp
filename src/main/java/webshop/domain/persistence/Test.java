package webshop.domain.persistence;

import java.sql.*;
import java.io.*;

public class Test {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "lucca", "Burdeos1");
			
			File imgfile=new File("C:\\Users\\Lucca.LAPTOP-PKB9NQVU\\Pictures\\scathach.png");
			FileInputStream fin = new FileInputStream(imgfile);
			    PreparedStatement pre = con.prepareStatement("insert into Klanten values(?,?,?,?,?,?,?)");
			    pre.setString(1, "Lucca");
			    pre.setBinaryStream(2, fin, (int) imgfile.length());
			    pre.setInt(3, 1);
			    pre.setString(4, "06-JUN-99");
			    pre.setString(5, "M");
			    pre.setInt(6, 06);
			    pre.setString(7, "@");
			    pre.executeUpdate();
			    pre.close();
			    con.commit();
			    con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
