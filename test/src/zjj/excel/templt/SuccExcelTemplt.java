package zjj.excel.templt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ist.common.util.DateUtils;
import com.zjj.common.properties.SysConfig;

import zjj.data.dto.OneDay_Count;
import zjj.data.util.DataUtil;
import zjj.database.connect.DataConnection;

/**
 * 具体实际操作excel的操作类
 * @author Administrator
 *
 */
public class SuccExcelTemplt extends DataConnection{
	
	
	public static String[] tableHead = { "姓名", "技师", "98", "128", "168", "加班",
			"点钟" };
	public static String[] tableHead2 = {"姓名", "技师", "238", "298", "加班" };
	
	public static void readExcel(File file){
		
		
		getConn();
		// 每次统计的时候先删除一下数据库在插入
		String sqlTru = "delete from oneDay_count where 1>0";
		String sqlMonth = "delete from month_Count where day=?";
		String sqlIns = "insert into oneDay_count values(?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sqlTru);
			pst.execute();
			pst = con.prepareStatement(sqlMonth);
			pst.setString(1, DateUtils.getCurrDate());
			pst.execute();
			pst = con.prepareStatement(sqlIns);

			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			Object value = null;
			XSSFRow row = null;
			XSSFCell cell = null;

			for (int i = sheet.getFirstRowNum() + 1; i <= sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null) {
						pst.setString(8, "0");
						continue;
					}
					DecimalFormat df = new DecimalFormat("0");// 格式化 number
					// String
					// 字符
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
					DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle()
								.getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle()
								.getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell
									.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}
					if (j == 1) {
						if (j == 1 && value != null) {
							pst.setString(8, "1");
						} else {
							pst.setString(8, "0");
						}
						continue;
					}
					if (value != null && !value.equals("")) {
						value = value.toString().replace(".00", "");
						if (j == 0) {
							pst.setString(1, (String) value);
						} else if (j == 2) {
							if (value.equals("98")) {
								pst.setString(2, "1");
								pst.setString(3, "0");
								pst.setString(4, "0");
								pst.setString(5, "0");
								pst.setString(6, "0");

							} else if (value.equals("128")) {
								pst.setString(2, "0");
								pst.setString(3, "1");
								pst.setString(4, "0");
								pst.setString(5, "0");
								pst.setString(6, "0");
							} else if (value.equals("168")) {
								pst.setString(2, "0");
								pst.setString(3, "0");
								pst.setString(4, "1");
								pst.setString(5, "0");
								pst.setString(6, "0");
							} else if (value.equals("238")) {
								pst.setString(2, "0");
								pst.setString(3, "0");
								pst.setString(4, "0");
								pst.setString(5, "1");
								pst.setString(6, "0");
							} else if (value.equals("298")) {
								pst.setString(2, "0");
								pst.setString(3, "0");
								pst.setString(4, "0");
								pst.setString(5, "0");
								pst.setString(6, "1");
							}
							pst.setString(7, "0");
							pst.addBatch();
						}
					}
				}
			}

			// 读取完毕统一执行sql
			pst.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	
	
	
	public static void createExcelType98(String filename){
		Map<String, List> map = new HashMap<String, List>();
		List<OneDay_Count> list = new ArrayList<OneDay_Count>();
		OneDay_Count odc = null;

		FileOutputStream ops = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();// 创建工作表
		sheet.setDefaultColumnWidth(8);
		List<String> listUser = DataUtil.getAllUseridByType("1");
		double height= 19.5*20 ; 
		for (int c = 0; c < listUser.size()+1; c++) {
			HSSFRow row = sheet.createRow(c);// 创建行
			if (c == 0) {
				row.setHeight((short)height);
				for (int j = 0; j < 7; j++) {

					HSSFCell cell = row.createCell(j);// 创建列
					/**
					 * 设置其它数据 设置风格
					 */
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
					style.setWrapText(true); // 文本区域随内容多少自动调整
					// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
					HSSFFont font = workbook.createFont();
					style.setFont(font);
					style.setWrapText(true);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderTop(HSSFCellStyle.BORDER_THIN);
					
					cell.setCellStyle(style);

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					cell.setCellValue(tableHead[j]);

				}
			} else {
				row.setHeight((short)height);
				String userid = listUser.get(c-1);
				
				/**
				 * 通过每个不同的用户id来查询数据
				 */
				odc = DataUtil.getOneDay_Count98(userid);
				/**
				 * 把数据插入到月表
				 */
				DataUtil.insertIntoMonth(DateUtils.getCurrDate(), odc);
				for (int j = 0; j < 7; j++) {
					HSSFCell cell = row.createCell(j);// 创建列
					/**
					 * 设置其它数据 设置风格
					 */
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
					style.setWrapText(true); // 文本区域随内容多少自动调整
					// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
					HSSFFont font = workbook.createFont();
					style.setFont(font);
					style.setWrapText(true);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderTop(HSSFCellStyle.BORDER_THIN);
					cell.setCellStyle(style);

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					if (j == 0) {
						cell
								.setCellValue(SysConfig
										.getProperty("database.name_"
												+ userid));
					} else if (j == 1) {
						cell.setCellValue(userid + "号");
					} else if (j == 2) {
						cell
								.setCellValue((odc.getType98() != null && !odc
										.getType98().equals("")) ? odc
										.getType98() : "");
					} else if (j == 3) {
						cell.setCellValue((odc.getType128() != null && !odc
								.getType128().equals("")) ? odc.getType128()
								: "");
					} else if (j == 4) {
						cell.setCellValue((odc.getType168() != null && !odc
								.getType168().equals("")) ? odc.getType168()
								: "");
					} else if (j == 5) {
						cell
								.setCellValue((odc.getJiaban() != null && !odc
										.getJiaban().equals("")) ? odc
										.getJiaban() : "");
					} else if (j == 6) {
						cell
								.setCellValue((odc.getDianzhongCount() != null && !odc
										.getDianzhongCount().equals("")) ? odc
										.getDianzhongCount() : "");
					}

				}
			}
		}
		try {
			ops = new FileOutputStream(filename);
			workbook.write(ops);
			ops.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void createExcelType298(String filename){
		OneDay_Count odc = null;

		FileOutputStream ops = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();// 创建工作表
		sheet.setDefaultColumnWidth(8);
		List<String> listUser = DataUtil.getAllUseridByType("2");
		double height= 19.5*20 ; 
		for (int c = 0; c < listUser.size()+1; c++) {
			HSSFRow row = sheet.createRow(c);// 创建行
			if (c == 0) {
				row.setHeight((short)height);
				for (int j = 0; j < 5; j++) {

					HSSFCell cell = row.createCell(j);// 创建列
					/**
					 * 设置其它数据 设置风格
					 */
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
					style.setWrapText(true); // 文本区域随内容多少自动调整
					// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
					HSSFFont font = workbook.createFont();
					style.setFont(font);
					style.setWrapText(true);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderTop(HSSFCellStyle.BORDER_THIN);
					
					cell.setCellStyle(style);

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					cell.setCellValue(tableHead2[j]);

				}
			} else {
				row.setHeight((short)height);
				String userid = listUser.get(c-1);
				
				odc = DataUtil.getOneDay_Count238(userid);
				
				/**
				 * 把数据插入到月表
				 */
				DataUtil.insertIntoMonth(DateUtils.getCurrDate(), odc);
				for (int j = 0; j < 5; j++) {

					HSSFCell cell = row.createCell(j);// 创建列
					/**
					 * 设置其它数据 设置风格
					 */
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
					style.setWrapText(true); // 文本区域随内容多少自动调整
					// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
					HSSFFont font = workbook.createFont();
					style.setFont(font);
					style.setWrapText(true);
					style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					style.setBorderRight(HSSFCellStyle.BORDER_THIN);
					style.setBorderTop(HSSFCellStyle.BORDER_THIN);
					cell.setCellStyle(style);

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					if (j == 0) {
						cell
								.setCellValue(SysConfig
										.getProperty("database.name_"
												+ odc.getUserid()));
					} else if (j == 1) {
						cell.setCellValue(odc.getUserid() + "号");
					} else if (j == 2) {
						cell.setCellValue((odc.getType238() != null && !odc
								.getType238().equals("")) ? odc.getType238()
								: "");
					} else if (j == 3) {
						cell.setCellValue((odc.getType298() != null && !odc
								.getType298().equals("")) ? odc.getType298()
								: "");
					}else if (j == 4) {
						cell
						.setCellValue((odc.getJiaban() != null && !odc
								.getJiaban().equals("")) ? odc
								.getJiaban() : "");
					}

				}
			}
		}
		try {
			ops = new FileOutputStream(filename);
			workbook.write(ops);
			ops.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}
