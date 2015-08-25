package zjj.excel;
import java.io.File;
import java.io.IOException;

import zjj.database.connect.DataConnection;
import zjj.excel.templt.SuccExcelTemplt;
/**
 * 成功以后的读取excel 逻辑操作类
 * @author Administrator
 *
 */
public class SuccExcel extends DataConnection {

	public static StringBuffer sb = new StringBuffer();


	/**
	 * 读取excel
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static void readExcel(File file) throws IOException {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			read2003Excel(file);
		} else if ("xlsx".equals(extension)) {
			read2007Excel(file);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}

	private static void read2003Excel(File file)
			throws IOException {
		SuccExcelTemplt.readExcel(file);
	}

	/**
	 * 读取Office 2007 excel 并且存入数据库
	 * */
	private static void read2007Excel(File file) throws IOException {
		SuccExcelTemplt.readExcel(file);
	}

	/**
	 * 生成excel文件 这个是针对98 128 168的项目类别来的。
	 * 
	 * @param filename
	 */
	public static void creatExcelByPoiUserType1(String filename) {
		SuccExcelTemplt.createExcelType98(filename);

	}


	
	
	/**
	 * 生成excel文件 这个是针对238 、298的项目类别来的。
	 * 
	 * @param filename
	 */
	public static void creatExcelByPoiUserType2(String filename) {
		SuccExcelTemplt.createExcelType298(filename);
	}
	
	
	
}
