package com.dhsr.wx.cp.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class UpLoadExcel {
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 要读取的excel文件的数据 例如：
     * A   B   C   D   E   F
     * 11  12      14  15  16
     * 21      23      25  26
     * ...
     * ...
     * 有些行的部分列为空，但其后面的列又有值
     */
    public List<String[]> getExcelTest(InputStream is) {
        // 声明集合 List<String[]> ，
        // List<String[]> 的元素 行数组String[]为excel中的每一行
        List<String[]> list = new ArrayList<String[]>();

        try {
            // 将is流实例到 一个excel流里
            HSSFWorkbook hwk = new HSSFWorkbook(is);
            // 得到book第一个工作薄sheet
            HSSFSheet sh = hwk.getSheetAt(0);
            // 总行数
            int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
            for (int i = 0; i < rows; i++) {
                HSSFRow row = sh.getRow(i);
                int cols = row.getLastCellNum() + 1 - row.getFirstCellNum(); // 该行的总列数
                String[] str = new String[cols]; // 用来存放该行每一列的值
                for (int j = 0; j < cols; j++) {
                    Object col = row.getCell((short) j);
                    Object colNext = row.getCell((short) (j + 1));
                    if (col != null) { // 该列不为空，直接读到 行数组里
                        str[j] = col.toString();
                    } else { // 该列为空
                        // 该列的后面一列不为空，用空字符串占位
                        if (colNext != null) {
                            Object colValue = "";
                            str[j] = colValue.toString();
                        }
                    }
                }
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 解决excel类型问题，获得数值
    public String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            // 数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                    ;
                } else {// 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    // 解决1234.0 去掉后面的.0
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            // 字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                // 读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            // ((Log) Logger.getLogger(value)).error("excel出现空值");
            // break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                value = "";
                ((Log) Logger.getLogger(value)).error("excel出现故障");
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }

    /**
     * 解决Excel类型问题(另一种)
     *
     * @param cell
     * @return
     */
    public static String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String value = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        value = "";
                    }
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_FORMULA: // 导入时如果为公式生成的数据则无值
                // System.out.println("Formula:" + cell.getStringCellValue());
                if (!cell.getStringCellValue().equals("")) {
                    value = cell.getStringCellValue();
                } else {
                    value = cell.getNumericCellValue() + "";
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                break;
            default:
                value = "";
        }
        return value;
    }

    /***
     *
     * @param in   POIFSFileSystem
     * @param max  所导入excel的列数
     * @return map
     * @throws IOException
     */
    public static Map<Integer, Map<Integer, List<String>>> getExcel(InputStream in, int max) throws IOException {
        Map<Integer, Map<Integer, List<String>>> map = new HashMap<Integer, Map<Integer, List<String>>>();// 总map
        Map<Integer, List<String>> sheetMap = null;// 每个sheet的map
        List<String> list = null;// 每行一个list
        // HSSFWorkbook workBook = null;

        Workbook workBook = null;
        try {
            workBook = new XSSFWorkbook(in);
        } catch (Exception ex) {
            System.out.println("====yichang");
            workBook = new HSSFWorkbook(new POIFSFileSystem(in));
        }

        /*
         * try { workBook = new HSSFWorkbook(in); } catch (final Exception e) {
         * throw new IOException("读取上传文件失败"); }
         */
        /**
         * 获得Excel中工作表个数
         */
        // sheet.autoSizeColumn(( short ) 0 );//导出自动适应宽度
        int sheetSize = workBook.getNumberOfSheets();
        // System.out.println("工作表个数 :" + sheetSize);
        for (int i = 0; i < sheetSize; i++) {
            sheetMap = new HashMap<Integer, List<String>>();
            // System.out.println("工作表名称:" + workBook.getSheetName(i));
            Sheet sheet = workBook.getSheetAt(i);
            int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
            if (rows > 0) {
                for (int j = 1; j < rows; j++) { // 行循环
                    list = new ArrayList<String>();
                    Row row = sheet.getRow(j);
                    if (row != null) {
                        int cells = row.getLastCellNum();// 获得列数
                        if (cells < max) {
                            cells = max;
                        }
                        for (short k = 0; k < cells; k++) { // 列循环
                            Cell cell = row.getCell(k);
                            String value = "";
                            if (cell != null) {
                                switch (cell.getCellType()) {
                                    case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
                                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                            // 如果是date类型则 ，获取该cell的date值
                                            value = DateTimeUtil.formatDateTimetoString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));

                                        } else {// 纯数字
                                            /*
                                             * value = String.valueOf(cell
                                             * .getNumericCellValue());
                                             * System.out.println(value+
                                             * "===========value");
                                             */
                                            BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                                            value = big.toString();
                                            // 解决1234.0 去掉后面的.0
                                            if (null != value && !"".equals(value.trim())) {
                                                String[] item = value.split("[.]");
                                                if (1 < item.length && "0".equals(item[1])) {
                                                    value = item[0];
                                                }
                                            }
                                            // System.out.println(value+"===========value");
                                        }
                                        if (value.matches("^((\\d+\\.?\\d+)[Ee]{1}(\\d+)){1}$")) {
                                            DecimalFormat df = new DecimalFormat("#.##");
                                            value = df.format(Double.parseDouble(value));
                                        }
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING: // 字符串型
                                        value = cell.getRichStringCellValue().toString().trim();
                                        break;
                                    case HSSFCell.CELL_TYPE_FORMULA:// 公式型
                                        // 读公式计算值
                                        value = String.valueOf(cell.getNumericCellValue());
                                        if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                                            value = cell.getRichStringCellValue().toString();
                                        }
                                        break;
                                    case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
                                        value = "" + cell.getBooleanCellValue();
                                        break;
                                    /* 此行表示该单元格值为空 */
                                    case HSSFCell.CELL_TYPE_BLANK: // 空值
                                        value = "";
                                        break;
                                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                                        value = "";
                                        break;
                                    default:
                                        value = cell.getRichStringCellValue().toString().trim();
                                }
                            }
                            list.add(value);
                        }
                        if (!isAllNull(list)) {
                            sheetMap.put(j, list);
                        }
                    }
                }
            }
            map.put(i, sheetMap);
        }
        return map;
    }

    /**
     * 如果list里面的值全为空 则范围true 反之则为false
     *
     * @param l list
     * @return
     */
    private static boolean isAllNull(List<String> l) {
        int i = 0;
        for (String s : l) {
            if (!"".equals(s)) {
                i++;
            }
        }
        if (i > 0) {
            return false;
        }
        return true;
    }

}
