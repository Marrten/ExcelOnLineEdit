package com.rivercloud.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelUtils {
    private Workbook wb;
    private Sheet sheet;
    private Row row;
 
    public ReadExcelUtils(File file) {
        String ext = file.getName().substring(file.getName().lastIndexOf("."));
        try {
            FileInputStream is = new FileInputStream(file);
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
     
    /**
     * 读取Excel数据内容
     * 
     */
    public Map<Integer, Object[]> readExcelContent() throws Exception{
        if(wb==null){
            throw new Exception("Workbook对象为空！");
        }
        Map<Integer, Object[]> content = new HashMap<Integer, Object[]>();
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        //得到标题
        Object[] title = new Object[colNum];
        //Map title = new HashMap();
        for (int i = 0; i <= colNum-1; i++) {
            Object obj = getCellFormatValue(row.getCell(i));
            title[i] = obj;
        }
        //得到正文内容
        for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                Object[] cellValue = new Object[colNum];
                Map cellvalue = new HashMap();
            for (int j = 0;j <= colNum-1;j++){
                    Object obj = getCellFormatValue(row.getCell(j));
                    cellValue[j] = obj;
            }
            content.put(0,title);
            content.put(i, cellValue);
        }
        return content;
    }
 
    /**
     * 
     * 根据Cell类型设置数据
     * 
     */
    private Object getCellFormatValue(Cell cell) {
        Object result = "";

        /*if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cell.setCellType(1);
                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }*/
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_STRING){//字符串类型
            result = cell.getStringCellValue();
        }
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_NUMERIC){//数值类型
            if (DateUtil.isCellDateFormatted(cell)) {
                result = cell.getDateCellValue();
            }else {
                Long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                    result = longVal;
                }
                else{
                    result = doubleVal;
                }
            }
        }
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){//布尔类型
            result = cell.getBooleanCellValue();
        }
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_FORMULA){//表达式类型
            result = cell.getCellFormula();
        }
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_ERROR){//错误类型
            result = cell.getErrorCellValue();
        }
        if (cell !=null&&cell.getCellType() == Cell.CELL_TYPE_BLANK){//空
        }
        return result.toString();
    }
 
}

