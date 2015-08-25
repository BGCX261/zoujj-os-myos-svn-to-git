package zjj.database.connect;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 邹建军
 *jdbc连接数据库
 */
public class DataConnection {
	//数据连接对象con
	public static Connection con = null;
	//预编译的语句pst
	public static PreparedStatement pst = null;
	//结果集对象rs
	public static ResultSet rs = null;
	
	private static String protocol = "jdbc:derby:";
	private static String dbName = "C:/db-derby-10.9.1.0-bin/bin/firstdb";
	/**
	 * 获取连接对象
	 */
	public static void getConn(){
		
		try {
			//加载驱动
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection(protocol + dbName+ ";create=true");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭资源
	 */
	public void close(){
		try {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试这个数据库连接是否成功
	 * */
//	public static void main(String[] args) {
//		new DataConnection().getConn();
//	}
}
