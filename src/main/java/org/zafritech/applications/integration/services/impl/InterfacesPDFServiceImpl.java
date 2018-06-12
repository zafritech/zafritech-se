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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.constants.PDFConstants;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.domain.InterfaceIssueComment;
import org.zafritech.applications.integration.data.domain.InterfaceType;
import org.zafritech.applications.integration.data.domain.Reference;
import org.zafritech.applications.integration.services.InterfacesPDFService;

/**
 *
 * @author Luke Sibisi
 */
@Service
public class InterfacesPDFServiceImpl implements InterfacesPDFService {

    @Override
    public void pdfAddFrontPage(Document document, Interface iface) throws DocumentException {
      
        // Add Space above Document Title
        Paragraph space = new Paragraph(" ", PDFConstants.NORMAL);
        addEmptyLine(space, 5);
        document.add(space);

        // Add Identifier Title
        Paragraph identifier = new Paragraph(iface.getSystemId(), PDFConstants.SUBTITLE);
        identifier.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(identifier, 4);
        document.add(identifier);

        // Add Document Title
        Paragraph title = new Paragraph(iface.getProject().getProjectName(), PDFConstants.TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add Project Title
        Paragraph primary = new Paragraph(iface.getPrimaryElement().getName(), PDFConstants.TITLE);
        primary.setAlignment(Element.ALIGN_CENTER);
        document.add(primary);

        // Add Project Title
        Paragraph secondary = new Paragraph(iface.getSecondaryElement().getName(), PDFConstants.TITLE);
        secondary.setAlignment(Element.ALIGN_CENTER);
        document.add(secondary);   
    }
    
    @Override
    public void pdfAddInterfaceDetails(Document document, Interface iface) throws DocumentException  {
       
        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        Paragraph para;
        
        if (null == iface.getStatus()) {
            
            statusFont = PDFConstants.TABLE_CELL_SMALL;
            
        } else switch (iface.getStatus()) {
            
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
        PdfPTable interfaceInfo = new PdfPTable(colWidths);
        interfaceInfo.setTotalWidth(555);
        interfaceInfo.setLockedWidth(true);
        interfaceInfo.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceInfo.getDefaultCell().setBorderColor(BaseColor.GRAY);
        
        // Interface Description label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Interface Name:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Name cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getInterfaceTitle(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        cell.setColspan(3);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Interface Description label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Description:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Description data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getInterfaceDescription(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        cell.setColspan(3);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Primary Entity label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Primary Entity:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Entity data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getPrimaryEntity().getCompanyCode(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Primary Entity label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Secondary Entity:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Entity data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getSecondaryEntity().getCompanyCode(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Primary Element label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Primary Element:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Element data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getPrimaryElement().getSbs() + " " + iface.getPrimaryElement().getName(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Primary Element label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Secondary Element:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Primary Element data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getSecondaryElement().getSbs() + " " + iface.getSecondaryElement().getName(), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Interface Level label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Interface Level:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Level data cell
        phrase = new Phrase();
        phrase.add(new Chunk("Level " + String.valueOf(iface.getInterfaceLevel()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Interface Status label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Status:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        // Interface Status data cell
        phrase = new Phrase();
        phrase.add(new Chunk(iface.getStatus().name().replace("INTERFACE_STATUS_", ""), statusFont));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Interface Types label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Interface Types:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        para = new Paragraph(); 
        
        List<InterfaceType> types = iface.getInterfaceTypes();
        
        for (InterfaceType type: types) {
            
            para.add(new Chunk(type.getTypeCode(), PDFConstants.TABLE_CELL_SMALL));
        }
        
        // Interface Types data cell
        phrase = new Phrase();
        phrase.add(para);
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
        
        // Interface References label cell
        phrase = new Phrase();
        phrase.add(new Chunk("References:", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableHeaderCellProperties(cell));
        
        para = new Paragraph(); 
        
        List<Reference> refs = new ArrayList(iface.getReferences());
        
        for (Reference ref : refs) {
            
            para.add(new Chunk(ref.getNumber(), PDFConstants.TABLE_CELL_SMALL));
            para.add(Chunk.NEWLINE);
        }
        
        // Interface References data cell
        phrase = new Phrase();
        phrase.add(para);
        cell = new PdfPCell(phrase);
        interfaceInfo.addCell(setTableCellProperties(cell));
                
        document.add(interfaceInfo);
    }
    
    @Override
    public void pdfAddInterfaceIssuesTitle(Document document, Interface iface) throws DocumentException {
        
        PdfPCell cell;
        Phrase phrase;

        float[] colWidths = {1};
        PdfPTable interfaceIssuesTitle = new PdfPTable(colWidths);
        interfaceIssuesTitle.setTotalWidth(555);
        interfaceIssuesTitle.setLockedWidth(true);
        interfaceIssuesTitle.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceIssuesTitle.getDefaultCell().setBorderColor(BaseColor.GRAY);

        // Add Space above Issue Title
        Paragraph space = new Paragraph(" ", PDFConstants.NORMAL);
        document.add(space);

        // Interface Header cell
        phrase = new Phrase();
        phrase.add(new Chunk("INTERFACE ISSUES", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(30f); 
        cell.setPaddingTop(10);
        interfaceIssuesTitle.addCell(setTableHeaderCellProperties(cell));
        
        document.add(interfaceIssuesTitle);
    }
    
    @Override
    public void pdfAddInterfaceIssues(Document document, Interface iface) throws DocumentException {
        
        List<InterfaceIssue> issues = iface.getIssues();

        for (InterfaceIssue issue : issues) {

            pdfAddIssueColumnLabels(document, issue);
            pdfAddIssueColumnsData(document, issue);

            // Add interface comments
            List<InterfaceIssueComment> comments = issue.getComments();
                
            if (comments.size() > 0) {
                
                pdfAddInterfaceIssueComments(document, issue);


            } else {

                pdfAddInterfaceIssueNoComments(document, issue);
            }
        }
    }
    
    @Override
    public void pdfAddInterfaceIssueComments(Document document, InterfaceIssue issue) throws DocumentException {
    
        float[] commentsColWidths = {2, 7, 2, 2, 3, 2};
        PdfPTable table = new PdfPTable(commentsColWidths);
        table.setTotalWidth(555);
        table.setLockedWidth(true);
        table.setHeaderRows(2); 
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.getDefaultCell().setBorderColor(BaseColor.GRAY);

        table = pdfAddIssueCommentsColumnLabels(table);

        // Add interface comments
        List<InterfaceIssueComment> comments = issue.getComments();
                
        for (InterfaceIssueComment comment : comments) {

            table = pdfAddIssueCommentsColumnsData(comment, table);
        }
        
        document.add(table);
    }

    @Override
    public void pdfAddInterfaceIssueNoComments(Document document, InterfaceIssue issue) throws DocumentException {
        
        PdfPCell cell;
        Phrase phrase;
    
        float[] commentsColWidths = {1};
        PdfPTable interfaceIssuesComments = new PdfPTable(commentsColWidths);
        interfaceIssuesComments.setTotalWidth(555);
        interfaceIssuesComments.setLockedWidth(true);
        interfaceIssuesComments.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceIssuesComments.getDefaultCell().setBorderColor(BaseColor.GRAY);
                
        // No issue comments found cell
        phrase = new Phrase();
        phrase.add(new Chunk("This issue has no comments.", PDFConstants.TABLE_CELL_SMALL_RED));
        cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setPaddingTop(15);
        cell.setPaddingBottom(12);
        interfaceIssuesComments.addCell(cell);

        document.add(interfaceIssuesComments);
    }  
    
    @Override
    public void pdfAddInterfaceNoIssues(Document document, Interface iface) throws DocumentException {
     
        PdfPCell cell;
        Phrase phrase;
   
        float[] issueColWidths = {1};
        PdfPTable interfaceNoIssuesTitle = new PdfPTable(issueColWidths);
        interfaceNoIssuesTitle.setTotalWidth(555);
        interfaceNoIssuesTitle.setLockedWidth(true);
        interfaceNoIssuesTitle.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceNoIssuesTitle.getDefaultCell().setBorderColor(BaseColor.GRAY);
        
        // No issues found cell
        phrase = new Phrase();
        phrase.add(new Chunk("No issues have been identified for this interface.", PDFConstants.TABLE_CELL_SMALL_GREEN));
        cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOX);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setPaddingTop(15);
        cell.setPaddingBottom(12);
        interfaceNoIssuesTitle.addCell(cell);

        document.add(interfaceNoIssuesTitle);
    }
    
    @Override
    public PdfPCell setTableHeaderCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(new BaseColor(0xA9, 0x32, 0x26));   // Brown
        
        return cell;
    }
    
    @Override
    public PdfPCell setTableHeaderGrayCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.GRAY);
        
        return cell;
    }
    
    @Override
    public PdfPCell setTableHeaderLightGrayCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY); 
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
        return cell;
    }
    
    @Override
    public PdfPCell setTableCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        
        return cell;
    }
    
    @Override
    public void addEmptyLine(Paragraph paragraph, Integer lines) {

        for (int i = 0; i < lines; i++) {

            paragraph.add(new Paragraph(" "));
        }
    }

    private void pdfAddIssueColumnLabels(Document document, InterfaceIssue issue) throws DocumentException {
    
        PdfPCell cell;
        Phrase phrase;
    
        // Add Space above each Issue
        Paragraph space = new Paragraph(" ", PDFConstants.VERY_SMALL);
        document.add(space);
        
        // Issue details Table
        float[] issuesColWidths = {6, 1, 1, 1};
        PdfPTable interfaceIssuesColumnLabels = new PdfPTable(issuesColWidths);
        interfaceIssuesColumnLabels.setTotalWidth(555);
        interfaceIssuesColumnLabels.setLockedWidth(true);
        interfaceIssuesColumnLabels.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceIssuesColumnLabels.getDefaultCell().setBorderColor(BaseColor.GRAY);
        
        // Issue Title cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue No: " + issue.getSystemId(), new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnLabels.addCell(setTableHeaderGrayCellProperties(cell));

        // Interface Issue Date Created label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Created",  new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnLabels.addCell(setTableHeaderGrayCellProperties(cell));

        // Interface Issue Date Updated label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Updated",  new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnLabels.addCell(setTableHeaderGrayCellProperties(cell));

        // Interface Issue Status label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Status",  new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnLabels.addCell(setTableHeaderGrayCellProperties(cell));

        document.add(interfaceIssuesColumnLabels);
    }
    
    private void pdfAddIssueColumnsData(Document document, InterfaceIssue issue) throws DocumentException {
        
        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        
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
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Issue details Table
        float[] issuesColWidths = {6, 1, 1, 1};
        PdfPTable interfaceIssuesColumnsData = new PdfPTable(issuesColWidths);
        interfaceIssuesColumnsData.setTotalWidth(555);
        interfaceIssuesColumnsData.setLockedWidth(true);
        interfaceIssuesColumnsData.getDefaultCell().setBorder(Rectangle.BOX);
        interfaceIssuesColumnsData.getDefaultCell().setBorderColor(BaseColor.GRAY);

        // Interface Isuue data cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getSystemId() + " " + issue.getIssueTitle(), PDFConstants.TABLE_HEADER_SMALL));
        phrase.add(Chunk.NEWLINE);
        phrase.add(Chunk.NEWLINE);
        phrase.add(new Chunk(issue.getIssueDescription().replaceAll("\\<.*?>", "").replaceAll("&.*?;", " "), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase); 
        interfaceIssuesColumnsData.addCell(setTableCellProperties(cell));

        // Interface Issue Creation Date data cell
        phrase = new Phrase();
        phrase.add(new Chunk(dateFormat.format(issue.getCreationDate()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnsData.addCell(setTableCellProperties(cell));

        // Interface Issue Update Date data cell
        phrase = new Phrase();
        phrase.add(new Chunk(dateFormat.format(issue.getModifiedDate()), PDFConstants.TABLE_CELL_SMALL));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnsData.addCell(setTableCellProperties(cell));

        // Interface Issue Status data cell
        phrase = new Phrase();
        phrase.add(new Chunk(issue.getStatus().name().replace("INTERFACE_STATUS_", ""), statusFont));
        cell = new PdfPCell(phrase);
        interfaceIssuesColumnsData.addCell(setTableCellProperties(cell));

        document.add(interfaceIssuesColumnsData);
    }

    private PdfPTable pdfAddIssueCommentsColumnLabels(PdfPTable table) throws DocumentException {
     
        PdfPCell cell;
        Phrase phrase;
        
        // Comments Header cell
        phrase = new Phrase();
        phrase.add(new Chunk("Issue Comments", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        cell.setColspan(6);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment Date Created label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Created", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment Deatils label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Comment", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment By label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Made By", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment By label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Status", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment Action label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Action", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));

        // Comment Action label cell
        phrase = new Phrase();
        phrase.add(new Chunk("Action By", PDFConstants.TABLE_HEADER_SMALL));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderLightGrayCellProperties(cell));
   
        return table;
    }

    private PdfPTable pdfAddIssueCommentsColumnsData(InterfaceIssueComment comment, PdfPTable table) throws DocumentException {
    
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
}
