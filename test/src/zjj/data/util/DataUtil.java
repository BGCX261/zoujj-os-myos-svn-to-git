package zjj.data.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zjj.data.dto.OneDay_Count;
import zjj.database.connect.DataConnection;

/**
 * 数据插入 1类别表示 98、128 、168 2类别表示 238 298
 * 
 * @author Administrator
 * 
 */
public class DataUtil extends DataConnection {

	public static void main(String[] args) {
		updateUsertable("22");

	}

	public static void insertTableDate(String userid, String username,
			String type) {
		getConn();
		String sql = "insert into user_type values(?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, username);
			pst.setString(3, type);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void updateUsertable(String userid){
		getConn();
		String sql = "update user_type set userid =? where userid=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, "22,");
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	
	public static OneDay_Count getOneDay_Count238(String id) {
		OneDay_Count odc = new OneDay_Count();

		getConn();
		String etype238Count = "select userid,count(type238) from oneDay_Count where userid =? and  type238='1' group by userid,type238";
		String etype298Count = "select userid,count(type298) from oneDay_Count where userid =? and type298='1' group by userid,type298";
		odc.setUserid(id);
		try {

			pst = con.prepareStatement(etype238Count);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String type238 = rs.getString(2);
				odc.setType238(type238);
			}

			pst = con.prepareStatement(etype298Count);
			pst.setString(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				String type298 = rs.getString(2);
				odc.setType298(type298);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return odc;
	}
	
	
	public static OneDay_Count getOneDay_Count98(String id) {
		OneDay_Count odc = new OneDay_Count();

		getConn();
		String edianzhongCount = "select userid,count(dianzhong) from oneDay_Count where userid =? and dianzhong='1' group by userid,dianzhong";
		String etype98Count = "select userid,count(type98) from oneDay_Count where userid =? and  type98='1' group by userid,type98";
		String etype128Count = "select userid,count(type128) from oneDay_Count where userid =? and type128='1' group by userid,type128";
		String etype168Count = "select userid,count(type168) from oneDay_Count where userid =? and type168='1' group by userid,type168";

		try {
			pst = con.prepareStatement(edianzhongCount);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String userid = rs.getString(1);
				String dianzhong = rs.getString(2);
				odc.setUserid(userid);
				odc.setDianzhongCount(dianzhong);
			}

			pst = con.prepareStatement(etype98Count);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String userid = rs.getString(1);
				String type98 = rs.getString(2);
				odc.setUserid(userid);
				odc.setType98(type98);
			}

			pst = con.prepareStatement(etype128Count);
			pst.setString(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				String userid = rs.getString(1);
				String type128 = rs.getString(2);
				odc.setUserid(userid);
				odc.setType128(type128);
			}

			pst = con.prepareStatement(etype168Count);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String userid = rs.getString(1);
				String type168 = rs.getString(2);
				odc.setUserid(userid);
				odc.setType168(type168);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return odc;
	}

	public static List<String> getAllUseridByType(String userType) {
		List<String> useridList = new ArrayList<String>();

		getConn();
		String userids = "select userid from user_type where usertype=?";

		try {
			pst = con.prepareStatement(userids);
			pst.setString(1, userType);
			rs = pst.executeQuery();
			while (rs.next()) {
				String userid = rs.getString(1);
				useridList.add(userid);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return useridList;
	}
	
	//每天插入的时候要把综合的数据保存到月表中
	public static void insertIntoMonth(String date,OneDay_Count odc) {
		getConn();
		String insertInto = "insert into month_Count values(?,?,?,?,?,?,?,?,?)";
		

		try {
			con.setAutoCommit(false);
			pst.execute();
			pst = con.prepareStatement(insertInto);
			pst.setString(1, (odc.getUserid()==null || odc.getUserid().equals(""))?"0":odc.getUserid());
			pst.setString(2, (odc.getType98()==null || odc.getType98().equals(""))?"0":odc.getType98());
			pst.setString(3, (odc.getType128()==null || odc.getType128().equals(""))?"0":odc.getType128());
			pst.setString(4, (odc.getType168()==null || odc.getType168().equals(""))?"0":odc.getType168());
			pst.setString(5, (odc.getType238()==null || odc.getType238().equals(""))?"0":odc.getType238());
			pst.setString(6, (odc.getType298()==null || odc.getType298().equals(""))?"0":odc.getType298());
			pst.setString(7, (odc.getJiaban()==null || odc.getJiaban().equals(""))?"0":odc.getJiaban());
			pst.setString(8, (odc.getDianzhongCount()==null || odc.getDianzhongCount().equals(""))?")":odc.getDianzhongCount());
			pst.setString(9, date);
			pst.execute();
			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	

	// 插入数据
	public static void insertInto() {
		insertTableDate("2", "文凤", "1");
		insertTableDate("3", "成平", "1");
		insertTableDate("5", "龚雪", "1");
		insertTableDate("6", "周翠兰", "1");
		insertTableDate("7", "廖小平", "1");
		insertTableDate("8", "曾维琼", "1");
		insertTableDate("9", "邓燕", "1");
		insertTableDate("11", "杨显芳", "1");
		insertTableDate("12", "孟恩英", "1");
		insertTableDate("13", "徐红", "1");
		insertTableDate("15", "王付琼", "1");
		insertTableDate("16", "罗书芳", "1");
		insertTableDate("17", "李羽丽", "1");
		insertTableDate("18", "蒋小贵", "1");
		insertTableDate("21", "杨昌珍", "1");
		insertTableDate("22,", "王小英", "1");
		insertTableDate("23", "邓兴娇", "1");
		insertTableDate("26", "曾令春", "1");
		insertTableDate("27", "黄秀娟", "1");
		insertTableDate("28", "陶劝", "1");
		insertTableDate("29", "张前芬", "1");
		insertTableDate("31", "杜仲琼", "1");
		insertTableDate("32", "蒋吉双", "1");
		insertTableDate("33", "刘青芳", "1");
		insertTableDate("35", "何星星", "1");
		insertTableDate("36", "郑小英", "1");
		insertTableDate("38", "范昌玉", "1");
		insertTableDate("51", "肖伶俐", "1");
		insertTableDate("52", "张祝英", "1");
		insertTableDate("53", "常仕枚", "1");
		insertTableDate("55", "陈单单", "1");
		insertTableDate("56", "邓小方", "1");
		insertTableDate("57", "梁曦", "1");
		insertTableDate("58", "张静", "1");
		insertTableDate("59", "刘桂平", "1");
		insertTableDate("61", "梁晓英", "1");
		insertTableDate("62", "刘兰英", "1");
		insertTableDate("65", "陈春梅", "1");
		insertTableDate("66", "陈美", "1");
		insertTableDate("67", "刘立艳", "1");
		insertTableDate("68", "卜素英", "1");
		insertTableDate("69", "李远春", "1");
		insertTableDate("70", "唐安林", "1");
		insertTableDate("71", "谭祖容", "1");
		insertTableDate("72", "宁顺菊", "1");
		insertTableDate("76", "杨家敏", "1");
		insertTableDate("77", "明亭艳", "1");
		insertTableDate("80", "龚小青", "2");
		insertTableDate("81", "向好", "2");
		insertTableDate("83", "牟清兰", "2");
		insertTableDate("85", "李绍香", "2");
		insertTableDate("86", "蒋明峰", "2");
		insertTableDate("87", "李坤美", "2");
		insertTableDate("88", "周红平", "2");
		insertTableDate("91", "胡利慧", "2");
		insertTableDate("92", "王子芳", "2");
		insertTableDate("95", "丁露", "2");
		insertTableDate("96", "成定菊", "2");
		insertTableDate("98", "熊皎君", "2");
		insertTableDate("99", "杨天青", "2");
		insertTableDate("100", "高贵", "2");
		insertTableDate("103", "刘燕", "2");
		insertTableDate("106", "黄国", "2");
		insertTableDate("108", "杨天", "2");
		insertTableDate("111", "刘爽", "2");
		insertTableDate("112", "杨梅", "2");
		insertTableDate("116", "方春", "2");
		insertTableDate("133", "冉晓", "2");
		insertTableDate("135", "贺丽", "2");
		insertTableDate("138", "黄守琼", "2");
	}

}
