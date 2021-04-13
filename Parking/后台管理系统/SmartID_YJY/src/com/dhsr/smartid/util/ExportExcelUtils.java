package com.dhsr.smartid.util;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelUtils {

	public void selectUseruserExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, List<String> headerList, List<String> headers0, List<Integer> length0, List<List<Object>> result, OutputStream out) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为15个字节
		// sheet.setDefaultColumnWidth((short) 20);
		for (int i = 0; i < headerList.size(); i++) {
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, headers0.size() - 1));
		}
		// sheet.addMergedRegion(new Region(36, (short) 4, 36, (short) 5));
		for (int i = 0; i < length0.size(); i++) {
			sheet.setColumnWidth(i, length0.get(i) * 256);
		}

		// 生成一个样式 》 标题
		XSSFCellStyle style1 = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font1 = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font1.setFontName("宋体");
		font1.setFontHeightInPoints((short) 17);
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style1.setFont(font1);

		// 生成一个样式 》 标题
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		int j = 0;
		for (String header : headerList) {
			if (j == 0) {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style1);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			} else {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			}
			j++;
		}

		XSSFRow row2 = sheet.createRow(headerList.size());
		row2.setHeightInPoints(20);
		for (int i = 0; i < headers0.size(); i++) {
			XSSFCell cell = row2.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers0.get(i));
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		if (result != null) {
			int index = headerList.size() + 1;
			for (List<Object> m : result) {
				XSSFRow row = sheet.createRow(index);
				row.setHeightInPoints(20);
				int cellIndex = 0;
				for (Object str : m) {
					XSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					if(str!=null){
						cell.setCellValue(str.toString());
					}else{
						cell.setCellValue("");
					}
					cellIndex++;
				}
				index++;
			}
		}
	}

	public void selectAccountStatisticsExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, List<String> headerList, List<String> headers0, List<String> headers1, List<Integer> length0, List<List<Object>> result, OutputStream out) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为15个字节
		// sheet.setDefaultColumnWidth((short) 20);
		for (int i = 0; i < headerList.size(); i++) {
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, headers0.size() - 1));
		}
		sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size(), 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size(), 4, 6));
		/*sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size(), 7, 9));
		sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size(), 10, 12));*/
		sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size() + 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(headerList.size(), headerList.size() + 1, 7, 7));
		// sheet.addMergedRegion(new Region(36, (short) 4, 36, (short) 5));
		for (int i = 0; i < length0.size(); i++) {
			sheet.setColumnWidth(i, length0.get(i) * 256);
		}

		// 生成一个样式 》 标题
		XSSFCellStyle style1 = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font1 = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font1.setFontName("宋体");
		font1.setFontHeightInPoints((short) 17);
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style1.setFont(font1);

		// 生成一个样式 》 标题
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		int j = 0;
		for (String header : headerList) {
			if (j == 0) {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style1);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			} else {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			}
			j++;
		}

		XSSFRow row2 = sheet.createRow(headerList.size());
		row2.setHeightInPoints(20);
		for (int i = 0; i < headers0.size(); i++) {
			XSSFCell cell = row2.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers0.get(i));
			cell.setCellValue(text);
		}

		XSSFRow row3 = sheet.createRow(headerList.size() + 1);
		row3.setHeightInPoints(20);
		for (int i = 0; i < headers1.size(); i++) {
			XSSFCell cell = row3.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers1.get(i));
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		if (result != null) {
			int index = headerList.size() + 2;
			for (List<Object> m : result) {
				XSSFRow row = sheet.createRow(index);
				row.setHeightInPoints(20);
				int cellIndex = 0;
				for (Object str : m) {
					XSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					cell.setCellValue(str.toString());
					cellIndex++;
				}
				index++;
			}
		}
	}

	public void selectConsumptionStatisticsExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, List<String> headerList, List<String> headers0, List<Map<String, Object>> result, OutputStream out) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为15个字节
		// sheet.setDefaultColumnWidth((short) 20);
		for (int i = 0; i < headerList.size(); i++) {
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, headers0.size() - 1));
		}
		// sheet.addMergedRegion(new Region(36, (short) 4, 36, (short) 5));
		for (int i = 0; i < headers0.size(); i++) {
			sheet.setColumnWidth(i, 16 * 256);
		}

		// 生成一个样式 》 标题
		XSSFCellStyle style1 = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font1 = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font1.setFontName("宋体");
		font1.setFontHeightInPoints((short) 17);
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style1.setFont(font1);

		// 生成一个样式 》 标题
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		/*
		 * style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		 * style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 */
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		int j = 0;
		for (String header : headerList) {
			if (j == 0) {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style1);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			} else {
				XSSFRow row0 = sheet.createRow(j);
				row0.setHeightInPoints(20);
				for (int i = 0; i < headers0.size(); i++) {
					XSSFCell cell0 = row0.createCell(i);
					cell0.setCellStyle(style);
					XSSFRichTextString text0 = new XSSFRichTextString(header);
					cell0.setCellValue(text0);
				}
			}
			j++;
		}

		XSSFRow row2 = sheet.createRow(headerList.size());
		row2.setHeightInPoints(20);
		for (int i = 0; i < headers0.size(); i++) {
			XSSFCell cell = row2.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers0.get(i));
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		if (result != null) {
			int index = headerList.size() + 1;
			for (Map<String, Object> map : result) {
				XSSFRow row = sheet.createRow(index);
				row.setHeightInPoints(20);
				int cellIndex = 0;
				for (String header : headers0) {
					int flag = 0;
					for (String key : map.keySet()) {
						if (header.equals(key)) {
							XSSFCell cell = row.createCell(cellIndex);
							cell.setCellStyle(style);
							cell.setCellValue(map.get(key).toString());
							cellIndex++;
							flag = 1;
							break;
						}
					}
					if (flag == 0) {
						XSSFCell cell = row.createCell(cellIndex);
						cell.setCellStyle(style);
						cell.setCellValue("");
						cellIndex++;
					}
				}
				index++;
			}
		}
	}
}
