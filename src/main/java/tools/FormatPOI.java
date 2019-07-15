package tools;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormatPOI {

	/**
	 * 每行写入
	 * 
	 * @param dataList  数据
	 * @param propList  属性
	 * @param fieldName 第一行表头
	 * @return
	 * @throws Exception
	 */
	public static Workbook exportExcel(List<?> dataList, List<String> propList, List<String> fieldName)
			throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("list");
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < fieldName.size(); i++) {
			row.createCell(i).setCellValue(fieldName.get(i));
		}
		if (dataList.size() > 0) {
			Class<? extends Object> c = dataList.get(0).getClass();
			for (int i = 0; i < dataList.size(); i++) {
				Object obj = dataList.get(i);
				row = sheet.createRow(i + 1);
				for (int k = 0; k < propList.size(); k++) {
					Method method = c.getMethod("get" + propList.get(k));
					Object val = method.invoke(obj);
					row.createCell(k).setCellValue(val == null ? "" : val.toString());
				}
			}
		}
		return workbook;
	}

}
