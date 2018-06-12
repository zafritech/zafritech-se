/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.constants;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author LukeS
 */
@Component
public class CoreConstants {
   
    public static String IMAGES_DIR;
    public static String DEFUALT_APPLICATION_LOGO_IMAGE;
    public static String APPLICATION_TEMPLATE_NAME;
    
    private CoreConstants() {
        
    }
   
    @Value("${zafritech.paths.images-dir}")
    public void setImagePaths(String value) {
     
        IMAGES_DIR = value;
        DEFUALT_APPLICATION_LOGO_IMAGE = value + "zidingo-rms-logo.png";   
    }
 
    @Value("${zafritech.template.template-name}")
    public void setApplicationTemplateName(String value) {
     
        APPLICATION_TEMPLATE_NAME = value;
    }
    
    // PDF Document Page Margins
    public static float MARGIN_TOP_DEFAULT = 66f;
    public static float MARGIN_BOTTOM_DEFAULT = 60f;
    public static float MARGIN_LEFT_DEFAULT = 30f;
    public static float MARGIN_RIGHT_DEFAULT = 30f;
    public static float MARGIN_TOP_FIRST_PAGE = 120f;
    
    public static float TEXT_INDENT_LEFT = 56f;
    
    // PDF Document Font Styles
    public static Font TITLE = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    public static Font SUBTITLE = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    public static Font SUBSUBTITLE = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    public static Font NORMAL = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    public static Font SMALL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    public static Font VERY_SMALL = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    public static Font HEADER1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    public static Font HEADER2 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    public static Font HEADER3 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    public static Font HEADER4 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    public static Font INDENTIFIER = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
    public static Font TABLE_HEADER = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    public static Font TABLE_HEADER_SMALL = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    public static Font TABLE_CELL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    public static Font TABLE_CELL_SMALL = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    public static Font TABLE_CELL_SMALL_GREEN = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(0x28, 0xB4, 0x63));
    public static Font TABLE_CELL_SMALL_RED = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(0xE7, 0x4C, 0x3C));
    public static Font TABLE_CELL_BOLD = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    public static Font TABLE_CELL_BOLD_SMALL = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    public static Font HEADER_LABEL = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
    public static Font FOOTER_TEXT = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
}
