/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.zafritech.core.services.ExcelService;

/**
 *
 * @author LukeS
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    // Excel 2003 or 2007
    @Override
    public Workbook getExcelWorkbook(FileInputStream inputStream, String filePath) throws IOException {
        
        Workbook workbook = null;

        if (filePath.endsWith("xlsx")) {

            workbook = new XSSFWorkbook(inputStream);

        } else if (filePath.endsWith("xls")) {

            workbook = new HSSFWorkbook(inputStream);

        } else {

            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object getExcelCellValue(Cell cell) {
        
        switch (cell.getCellTypeEnum()) {

            case STRING:
                return cell.getStringCellValue();

            case BOOLEAN:
                return cell.getBooleanCellValue();

            case NUMERIC:
                return cell.getNumericCellValue();

            default:
                return "";
        }
    }

    @Override
    public Map<String, CellStyle> getExcelStyles(Workbook wb) {
        
        return createStyles(wb);
    }
    
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CreationHelper creationHelper = wb.getCreationHelper();
        
        CellStyle style;
        
        // Header Font
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex()); 
        
        // Green Font
        Font greenFont = wb.createFont();
        greenFont.setFontHeightInPoints((short)12);
        greenFont.setBold(true);
        greenFont.setColor(IndexedColors.GREEN.getIndex());

        // Red Font
        Font redFont = wb.createFont();
        redFont.setFontHeightInPoints((short)12);
        redFont.setBold(true);
        redFont.setColor(IndexedColors.RED.getIndex());        

        // Orange Font
        Font orangeFont = wb.createFont();
        orangeFont.setFontHeightInPoints((short)12);
        orangeFont.setBold(true);
        orangeFont.setColor(IndexedColors.ORANGE.getIndex());
        
        // Header Left Aligned Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT); 
        style.setVerticalAlignment(VerticalAlignment.CENTER); 
        style.setFont(headerFont);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("HeaderLeftAlign", style);
        
        // Header Center Aligned Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(headerFont);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("HeaderCenterAlign", style);
        
        // Body Left Aligned Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        styles.put("BodyLeftAlign", style);
        
        // Body Center Aligned Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        styles.put("BodyCenterAlign", style);
        
        // Body Left Aligned WrapText Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setWrapText(true); 
        styles.put("BodyLeftAlignWrapText", style);
        
        // Body Left Aligned Date Format Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss")); 
        styles.put("BodyLeftAlignDate", style);
        
        // Body Center Aligned Date Format Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss")); 
        styles.put("BodyCenterAlignDate", style);

        // Body Center Aligned GREEN Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFont(greenFont);
        styles.put("GreenCenterAlign", style);

        // Body Center Aligned RED Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFont(redFont);
        styles.put("RedCenterAlign", style);

        // Body Center Aligned RED Style
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFont(orangeFont);
        styles.put("OrangeCenterAlign", style);
        
        return styles;
    }
   
    private static CellStyle createBorderedStyle(Workbook wb) {
        
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        return style;
    }   
}
