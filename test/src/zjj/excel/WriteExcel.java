package zjj.excel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class WriteExcel {

	private static final Logger log = Logger.getAnonymousLogger();
	private static String xmlFile = String.valueOf(Calendar.getInstance()
			.getTimeInMillis())
			+ ".xls";
	private static String file_path = "C:/";

	public static void main(String[] args) {

		WriteExcel tpoi = new WriteExcel();
		String temp = file_path + xmlFile;
		log.info("-----temp:" + temp);
		try {
			tpoi.getExcelConmentByPOI(file_path + "user.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// tpoi.creatExcelByPOI(file_path+"whh.xls");

	}

	/**
	 * 生成excel文件
	 * 
	 * @param filename
	 */
	public static void creatExcelByPOI(String filename) {
		FileOutputStream ops = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();// 创建工作表
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 1000);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 2000000);
		sheet.setColumnWidth(7, 200000);
		sheet.setColumnWidth(8, 20000);
		sheet.setColumnWidth(9, 20000);
		// sheet.setDefaultColumnWidth((short)50);
		for (int i = 0; i < 10; i++) {
			log.info("-----i:" + "第" + i + "行");
			HSSFRow row = sheet.createRow(i);// 创建行
			for (int j = 0; j < 10; j++) {
				HSSFCell cell = row.createCell(j);// 创建列

				/**
				 * 设置其它数据 设置风格
				 */
				HSSFCellStyle style = workbook.createCellStyle();
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
				style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setLeftBorderColor(HSSFColor.BLACK.index);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setRightBorderColor(HSSFColor.BLACK.index);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setTopBorderColor(HSSFColor.BLACK.index);
				// style.setWrapText(true);//文本区域随内容多少自动调整
				// style.setFillForegroundColor(HSSFColor.LIME.index);
				// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
				style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setLeftBorderColor(HSSFColor.BLACK.index);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setRightBorderColor(HSSFColor.BLACK.index);
				style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				style.setTopBorderColor(HSSFColor.BLACK.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 最好的设置Pattern
				// 单元格背景的显示模式．
				style.setFillForegroundColor(new HSSFColor.BLUE_GREY()
						.getIndex()); // 设置单元格背景色;
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
				// style.setWrapText(true); //文本区域随内容多少自动调整
				// style.setFillPattern(HSSFCellStyle.//);
				// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
				HSSFFont font = workbook.createFont();
				font.setColor(HSSFFont.SS_NONE);
				// font.setFontHeightInPoints(24);
				font.setFontName("Courier New");
				// font.setItalic(true);
				// font.setStrikeout(true);//给字体加上删除线
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style.setFont(font);

				style.setWrapText(true);
				cell.setCellStyle(style);

				// cell.setCellNum((i*10+j));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);

				cell
						.setCellValue("今天是第"
								+ j
								+ "天，好好学习天天向上，解决方法我已经找到了，具体应该是这样的用poi包的hssf，怎样让excel中的单元格cell里面的文字自动换行啊");
				log.info("-----j:" + "第" + j + "列");
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

	/**
	 * 解析excel文件
	 * 
	 * @param filename
	 * 
	 */
	public void getExcelConmentByPOI(String filename) throws IOException {
		log.info("-----解析excel文件-----开始-----filename:" + filename);
		InputStream is = new FileInputStream(filename);
		try {
			HSSFWorkbook book = new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);// 获取第1个工作表
			HSSFRow row = sheet.getRow(0);
			log.info("-----sheet.getPhysicalNumberOfRows():"
					+ sheet.getPhysicalNumberOfRows());
			// 循环每个工作表
			for (int j = 0; j < book.getNumberOfSheets(); j++) {
				sheet = book.getSheetAt(j);// 获取第j个工作表
				// 获取第j个工作表的从第i行开始的数据
				log.info("-----第" + j + "个工作表------");
				for (int i = 0; i < sheet.getPhysicalNumberOfRows() + 1; i++) {
					log.info("-----第" + i + "行");
					row = sheet.getRow(i);
					if (row != null) {
						for (int z = 0; z < row.getPhysicalNumberOfCells(); z++) {
							HSSFCell cell = row.getCell(z);// 获取列的内容
							System.out.println(cell.getCellType());

							log.info("-----第" + z + "列,内容："
									+ cell.getStringCellValue());
						}
					} else {
						log.info("-----第" + i + "行内容为空");
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
		log.info("-----解析excel文件-----完毕-----");
	}

	/**
	 * 生成excel文件
	 * 
	 * @param filename
	 */
	public static void creatExcelByPoi(Map rowCloumMap, List list,
			String filename) {
		FileOutputStream ops = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();// 创建工作表

		Object[] objs = rowCloumMap.keySet().toArray();
		for (int l = 0; l < rowCloumMap.size(); l++) {

			// 一共有多少行
			int rowLast = Integer.parseInt((String) rowCloumMap.get(objs[l]));

			sheet.setDefaultColumnWidth(8);
			for (int i = 0; i < rowCloumMap.size(); i++) {
				HSSFRow row = sheet.createRow(i);// 创建行

				for (int j = 0; j < rowLast; j++) {
					HSSFCell cell = row.createCell(j);// 创建列
					/**
					 * 设置其它数据 设置风格
					 */
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
					style.setWrapText(true); // 文本区域随内容多少自动调整
					// 设置字体Color,首先创建Font对象,后对font设置,然后做为参数传给style
					HSSFFont font = workbook.createFont();
					font.setColor(HSSFFont.SS_NONE);
					font.setFontName("Courier New");
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					style.setFont(font);
					style.setWrapText(true);
					cell.setCellStyle(style);

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					cell.setCellValue("");
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
