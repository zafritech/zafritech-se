/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.constants.PDFConstants;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.domain.InterfaceIssueComment;
import org.zafritech.applications.integration.services.IssuesPDFService;

/**
 *
 * @author Luke Sibisi
 */
@Service
public class IssuesPDFServiceImpl implements IssuesPDFService {
  
    @Override
    public void pdfAddInterfaceIssue(Document document, InterfaceIssue issue) throws DocumentException {
     
        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (null == issue.getStatus()) {
            
            statusFont = PDFConstants.TABLE_CELL_SMALL;
            
        } else switch (issue.getStatus()) {
            
            case INTERFACE_STATUS_OPEN:
                
                statusFont = PDFConstants.TABLE_CELL_SMALL_RED;
                break;
                
            case INTERFACE_STATUS_CLOSED:
                
                statusFont = PDFConstants.TABLE_CELL_SMALL_GREEN;
                break;
                
            default:
                statusFont = PDFConstants.TABLE_CELL_SMALL;
                break;
        }
        
        float[] colWidths = {2, 3, 2, 3};
        PdfPTable issueInfo = new PdfPTable(colWidths);
        issueInfo.setTotalWidth(555);
        issueInfo.setLockedWidth(true);
        issueInfo.getDefaultCell().setBorder(Rectangle.BOX);
        issueInfo.getDefaultCell().setBorderColor(BaseColor.GRAY);
        
        // Primary Entity label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue No:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Entity data cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getSystemId(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableCellProperties(cell));
        
        // Primary Entity label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Created:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Entity data cell
        phrase = new Phrase();
        phrase.add(new Chunk(dateFormat.format(issue.getCreationDate()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableCellProperties(cell));
        
        // Primary Element label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Status:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Element data cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getStatus().name().replace("INTERFACE_STATUS_", ""), statusFont));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableCellProperties(cell));
        
        // Primary Element label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Updated:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Element data cell
        phrase = new Phrase();
        phrase.add(new Chunk(dateFormat.format(issue.getModifiedDate()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableCellProperties(cell));
        
        // Interface Description label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue Title:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Name cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getIssueTitle(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        cell.setColspan(3);
        issueInfo.addCell(setTableCellProperties(cell));
        
        // Interface Description label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue Description:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        issueInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Description data cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getIssueDescription().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        cell.setColspan(3);
        issueInfo.addCell(setTableCellProperties(cell));
        
        document.add(issueInfo);
    }
    
    @Override
    public void pdfAddIssueComments(Document document, InterfaceIssue issue) throws DocumentException {
        
        float[] commentsColWidths = {2, 7, 2, 2, 3, 2};
        PdfPTable table = new PdfPTable(commentsColWidths);
        table.setTotalWidth(555);
        table.setLockedWidth(true);
        table.setHeaderRows(2); 
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBorderColor(BaseColor.GRAY);
        
        table = addCommentLabels(table);

        // Add interface comments
        List<InterfaceIssueComment> comments = issue.getComments();
                
        for (InterfaceIssueComment comment : comments) {

            table = addCommentsData(table, comment);
        }
        
        // Add Space above Comments Table
        Paragraph space = new Paragraph(" ", PDFConstants.NORMAL);
        document.add(space);
        
        document.add(table);
    }
    
    private PdfPTable addCommentLabels(PdfPTable table) {
        
        PdfPCell cell;
        Phrase phrase;
        
        // Comments Header cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue Comments", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        cell.setColspan(6);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment Date Created label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Created", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment Deatils label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Comment", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment By label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Made By", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment By label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Status", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment Action label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Action", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));

        // Comment Action label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Action By", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
   
        return table;
    }
    
    private PdfPTable addCommentsData(PdfPTable table, InterfaceIssueComment comment) {
       
        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        
        if (null == comment.getStatus()) {
            
            statusFont = PDFConstants.TABLE_CELL_SMALL;
            
        } else switch (comment.getStatus()) {
            
            case INTERFACE_STATUS_OPEN:
                
                statusFont = PDFConstants.TABLE_CELL_SMALL_RED;
                break;
                
            case INTERFACE_STATUS_CLOSED:
                
                statusFont = PDFConstants.TABLE_CELL_SMALL_GREEN;
                break;
                
            default:
                statusFont = PDFConstants.TABLE_CELL_SMALL;
                break;
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Interface Issue Creation Date data cell
        phrase = new Phrase();
        phrase.add(new Chunk(dateFormat.format(comment.getCreationDate()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableCellProperties(cell));

        // Isuue Comment data cell
        phrase = new Phrase();
        phrase.add(new Chunk(comment.getComment().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase); 
        table.addCell(setTableCellProperties(cell));

        // Issue Created By data cell
        phrase = new Phrase();
        phrase.add(new Chunk(comment.getCommentBy().getDiplayCode(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableCellProperties(cell));

        // Issue Status data cell
        phrase = new Phrase();
        phrase.add(new Chunk(comment.getStatus().name().replace("INTERFACE_STATUS_", ""), statusFont));
        cell = new PdfPCell(phrase);
        table.addCell(setTableCellProperties(cell));

        // Issue Action data cell
        phrase = new Phrase();
        phrase.add(new Chunk(comment.getCommentAction().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableCellProperties(cell));

        // Issue Action By data cell
        phrase = new Phrase();
        phrase.add(new Chunk(comment.getActionBy(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableCellProperties(cell));
        
        return table;
    }
    
    private PdfPCell setTableHeaderCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(new BaseColor(0xA9, 0x32, 0x26));   // Brown
        
        return cell;
    }
    
    private PdfPCell setTableHeaderGrayCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.GRAY);
        
        return cell;
    }
    
    private PdfPCell setTableCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        
        return cell;
    }
    
    private PdfPCell setTableHeaderLightGrayCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
        return cell;
    }
}
