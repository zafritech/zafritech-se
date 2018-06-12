/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.util.AbstractMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.constants.PDFConstants;
import org.zafritech.applications.integration.data.converters.InterfaceListToInterfaceViewListConverter;
import org.zafritech.applications.integration.data.dao.InterfaceViewDao;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.services.SystemPDFReportService;
import org.zafritech.core.constants.CoreConstants;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
@Service
public class SystemPDFReportServiceImpl implements SystemPDFReportService {

    @Autowired
    IntegrationEntityRepository entityRepository;

    @Autowired
    ElementRepository elementRepository;

    @Autowired
    InterfaceRepository interfaceRepository;
    
    @Autowired
    private InterfaceListToInterfaceViewListConverter interfaceListConverter;
    
    @Override
    public void pdfAddFrontPage(Document document, Project project) throws Exception {
        
        PdfPCell cell;

       // Add Space above Document Title
        Paragraph space = new Paragraph(" ", PDFConstants.NORMAL);
        addEmptyLine(space, 4);
        document.add(space);

        // Add Project Title
        Paragraph projectName = new Paragraph(project.getProjectName(), PDFConstants.TITLE);
        projectName.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(projectName, 2);
        document.add(projectName);

        // Add Document Title
        Paragraph title = new Paragraph("System Technical Interfaces Report", PDFConstants.SUBTITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(title, 4);
        document.add(title);

        float[] colWidths = {1};
        PdfPTable projectLogoTable = new PdfPTable(colWidths);
        projectLogoTable.setTotalWidth(555);
        projectLogoTable.setLockedWidth(true);
        projectLogoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        
        Image projectLogoImage = Image.getInstance(PDFConstants.PROJECT_LOGO_IMAGE_PREFIX + project.getUuId() + ".jpg");
        projectLogoImage.setAlignment(Image.ALIGN_CENTER);
        projectLogoImage.scaleToFit(360f, 240f);
        cell = new PdfPCell(projectLogoImage);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(6f);
        cell.setBorder(Rectangle.NO_BORDER);
        projectLogoTable.addCell(cell);
        
        document.add(projectLogoTable);  
        
        // Add Space above Project Title
        space = new Paragraph(" ", PDFConstants.NORMAL);
        addEmptyLine(space, 1);
        document.add(space);
        
        // Add Project Title
        Paragraph line = new Paragraph("Lines 4, 5 & 6", PDFConstants.SUBTITLE);
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);   
    }
    
    @Override
    public void pdfAddTableOfContents(Document document, List<AbstractMap.SimpleEntry<String, Integer>> entries) throws Exception {
     
        // ===================================================
        // Add Table of Contents START
        // ===================================================
        document.newPage();
        Paragraph tocTitle = new Paragraph();
        tocTitle.add(new Chunk("Table of Contents", CoreConstants.HEADER1));
        addEmptyLine(tocTitle, 1);
        document.add(tocTitle);

        Paragraph para;

        Chunk dottedLine = new Chunk(new DottedLineSeparator());

        for (AbstractMap.SimpleEntry<String, Integer> entry : entries) {

            para = new Paragraph(entry.getKey());
            para.add(dottedLine);
            para.add(String.valueOf(entry.getValue()));
            document.add(para);
        }
        // ===================================================
        // END Table of Contents
        // ===================================================
    }
    
    @Override
    public void pdfAddSBSPage(Document document, Project project) throws Exception {
     
        PdfPCell cell;
        Phrase phrase;
        document.newPage();
        
        Paragraph distributionTitle = new Paragraph();
        distributionTitle.add(new Chunk("System Breakdown Structure", CoreConstants.HEADER1)); 
        addEmptyLine(distributionTitle, 1);
        document.add(distributionTitle);
        
        List<IntegrationEntity> entities = entityRepository.findByProjectAndHasElementsOrderBySortOrderAsc(project, true);
        
        for (IntegrationEntity entity : entities) {
         
            float[] commentsColWidths = {1, 6, 1};
            PdfPTable table = new PdfPTable(commentsColWidths);
            table.setTotalWidth(555);
            table.setLockedWidth(true);
            table.setHeaderRows(2); 
            table.getDefaultCell().setBorder(Rectangle.BOX);
            table.getDefaultCell().setBorderColor(BaseColor.GRAY);
            table.setComplete(false);
            
            phrase = new Phrase();
            phrase.add(new Chunk("SBS", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(setTableHeaderCellProperties(cell));
            
            phrase = new Phrase();
            phrase.add(new Chunk("System Element", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(setTableHeaderCellProperties(cell));
            
            phrase = new Phrase();
            phrase.add(new Chunk("Entity", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(setTableHeaderCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(entity.getSbs(),  new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell = new PdfPCell(phrase);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(entity.getCompany().getCompanyName(),  new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell = new PdfPCell(phrase);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(entity.getCompanyCode(),  new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell = new PdfPCell(phrase);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(setTableCellProperties(cell));
            
            document.add(table);
            
            List<org.zafritech.applications.integration.data.domain.Element> elements = elementRepository.findByEntityAndParentOrderBySortOrder(entity, null);
            
            for (org.zafritech.applications.integration.data.domain.Element element : elements) {

                phrase = new Phrase();
                phrase.add(new Chunk(element.getSbs(),  new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                cell = new PdfPCell(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(setTableCellProperties(cell));

                phrase = new Phrase();
                phrase.add(new Chunk(element.getName(),  new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                cell = new PdfPCell(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(setTableCellProperties(cell));

                phrase = new Phrase();
                phrase.add(new Chunk(element.getEntity().getCompanyCode(),  new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                cell = new PdfPCell(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(setTableCellProperties(cell));
                
                document.add(table);
                
                List<org.zafritech.applications.integration.data.domain.Element> subElements = elementRepository.findByEntityAndParentOrderBySortOrder(entity, element);
                
                for (org.zafritech.applications.integration.data.domain.Element subElement : subElements) {

                    phrase = new Phrase();
                    phrase.add(new Chunk(subElement.getSbs(),  new Font(Font.FontFamily.HELVETICA, 9)));
                    cell = new PdfPCell(phrase);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(setTableCellProperties(cell));

                    phrase = new Phrase();
                    phrase.add(new Chunk(subElement.getName(),  new Font(Font.FontFamily.HELVETICA, 9)));
                    cell = new PdfPCell(phrase);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(setTableCellProperties(cell));

                    phrase = new Phrase();
                    phrase.add(new Chunk(subElement.getEntity().getCompanyCode(),  new Font(Font.FontFamily.HELVETICA, 9)));
                    cell = new PdfPCell(phrase);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(setTableCellProperties(cell));
                }
                    
                document.add(table);
            }
            
            document.add(table);
            table.setComplete(true);
            
            // Space for next Entity Breakdown Structure
            Paragraph paragraph = new Paragraph(" ");
            document.add(paragraph);
        }
    }
    
    @Override
    public void pdfAddInterfacesStatus(Document document, Project project) throws Exception {
     
        document.newPage();

        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        
        Paragraph distributionTitle = new Paragraph();
        distributionTitle.add(new Chunk("System Interfaces Status", CoreConstants.HEADER1)); 
        addEmptyLine(distributionTitle, 1);
        document.add(distributionTitle);   
        
        
    }
    
    @Override
    public void pdfAddInterfaceMatrix(Document document, Project project) throws Exception {
        
        document.newPage();

        PdfPCell cell;
        Phrase phrase;
        Font statusFont;
        
        Paragraph distributionTitle = new Paragraph();
        distributionTitle.add(new Chunk("System Interface Matrix", CoreConstants.HEADER1)); 
        addEmptyLine(distributionTitle, 1);
        document.add(distributionTitle);

        // [#] [Pr Elem] [Pr Ent] [Sec Elm] [Sec Ent] [Title] [Level] [Desc] [Status] [ICD]
        float[] colWidths = {1, 6, 1, 6, 1, 6, 2, 6, 2, 4};
        PdfPTable table = new PdfPTable(colWidths);
        table.setTotalWidth(1130);
        table.setLockedWidth(true);
        table.setHeaderRows(1); 
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setComplete(false);
        
        phrase = new Phrase();
        phrase.add(new Chunk("#", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Primary Element", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Secondary Element", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        cell.setColspan(2);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Interface Title", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Level", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Description", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("Status", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
        
        phrase = new Phrase();
        phrase.add(new Chunk("ICD References", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell = new PdfPCell(phrase);
        table.addCell(setTableHeaderCellProperties(cell));
        
        document.add(table);
        
        List<InterfaceViewDao> interfaces = interfaceListConverter.convert(interfaceRepository.findAll());
        
        Integer count = 1;
        
        for (InterfaceViewDao iface: interfaces) {

            if (null == iface.getStatus()) {

                statusFont = PDFConstants.TABLE_CELL_SMALL;

            } else switch (iface.getStatus()) {

                case "INTERFACE_STATUS_OPEN":

                    statusFont = PDFConstants.TABLE_CELL_SMALL_RED;
                    break;

                case "INTERFACE_STATUS_CLOSED":

                    statusFont = PDFConstants.TABLE_CELL_SMALL_GREEN;
                    break;

                default:
                    statusFont = PDFConstants.TABLE_CELL_SMALL;
                    break;
            }

            phrase = new Phrase();
            phrase.add(new Chunk(String.valueOf(count), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getSecondaryElementSbs() + " "+ iface.getPrimaryElementName(), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getPrimaryEntityCode(), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getSecondaryElementSbs() + " "+ iface.getSecondaryElementName(), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getSecondaryEntityCode(), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getInterfaceTitle(), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk("Level " + String.valueOf(iface.getInterfaceLevel()), PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getInterfaceDescription() != null ? iface.getInterfaceDescription() : "", PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk(iface.getStatus().replace("INTERFACE_STATUS_", ""), statusFont));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));

            phrase = new Phrase();
            phrase.add(new Chunk("", PDFConstants.TABLE_CELL_SMALL));
            cell = new PdfPCell(phrase);
            table.addCell(setTableCellProperties(cell));
            
            if (count % 10 == 0) {
                
                document.add(table);
            }
            
            count++;
        }
        
        document.add(table);
        table.setComplete(true);
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
  
    private PdfPCell setTableCellProperties(PdfPCell cell) {
        
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setPadding(5);
        
        return cell;
    }
          
    private void addEmptyLine(Paragraph paragraph, Integer lines) {

        for (int i = 0; i < lines; i++) {

            paragraph.add(new Paragraph(" "));
        }
    }
}
