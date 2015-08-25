package data.derby.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyTest {
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";//内嵌
	//private static String driver = "org.apache.derby.jdbc.ClientDriver";//网络
	//String dbName = "jdbc:derby://localhost:1527/firstdb;create=true";
	//String dbName = "jdbc:derby:firstdb;create=true";
	
	private static String protocol = "jdbc:derby:";
	String dbName = "C:/db-derby-10.9.1.0-bin/bin/firstdb";

	static void loadDriver() {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doIt() {
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		System.out.println("starting");
		try {
			conn = DriverManager.getConnection(protocol + dbName+ ";create=true");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to and created database" + dbName);
		try {
			s = conn.createStatement();
			s.execute("insert into my_table values(3,'邹建军')");
			rs = s.executeQuery("select * from my_table");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			conn.close();
			conn = null;
			s.close();
			s = null;
			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DerbyTest t = new DerbyTest();
		t.loadDriver();
		t.doIt();
	}
}