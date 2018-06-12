/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.components;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.constants.PDFConstants;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.services.impl.ReportsPDFServiceImpl;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class IntegrationPDFClasses {

    public class HeaderTableIssue extends PdfPageEventHelper {
      
        PdfTemplate numberOfPages;

        protected PdfPTable header;
        protected float tableHeight;
        protected InterfaceIssue issue;

        public HeaderTableIssue(InterfaceIssue issue) {

            this.issue = issue;
        }
     
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {

            numberOfPages = writer.getDirectContent().createTemplate(30, 16);
        }
    
        public float getTableHeight() {

            return tableHeight;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            
            if (writer.getPageNumber() > 1) {
            
                document.setMargins(PDFConstants.MARGIN_LEFT_DEFAULT,
                                    PDFConstants.MARGIN_RIGHT_DEFAULT,
                                    PDFConstants.MARGIN_TOP_DEFAULT,
                                    PDFConstants.MARGIN_BOTTOM_DEFAULT);
            }
        }
      
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            if (writer.getPageNumber() == 1) {

                try {

                    addFrontHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {

                    addBodyHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
  
        private void addFrontHeaderTable(PdfWriter writer, Document document) throws Exception {

            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;
            
            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(issue.getIssueInterface().getProject().getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Blank Cell Row
            phrase = new Phrase();
            phrase.add(new Chunk(" ", PDFConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Document Title
            phrase = new Phrase();
            phrase.add(new Chunk("ISSUE STATUS REPORT", PDFConstants.SUBSUBTITLE));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setPaddingTop(5);
            cell.setPaddingBottom(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.BOX);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());
        }
     
        private void addBodyHeaderTable(PdfWriter writer, Document document) throws Exception {

            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(issue.getIssueInterface().getProject().getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());
        }     
    }
    
    public class FooterTableIssue extends PdfPageEventHelper {
       
        protected PdfPTable footer;
        protected Document document;
        protected InterfaceIssue issue;
        protected PdfTemplate template;
        protected Image total;

        public FooterTableIssue(Document document, InterfaceIssue issue) {

            this.document = document;
            this.issue = issue;
        }
         
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            PdfPCell cell;
            Phrase phrase;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String projectName = issue.getIssueInterface().getProject().getProjectName();

            float[] colWidths = {11, 7, 8, 1};
            footer = new PdfPTable(colWidths);
            footer.setTotalWidth(555);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // Project Name cell
            cell = new PdfPCell(new Phrase(projectName, PDFConstants.TABLE_CELL_SMALL));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            footer.addCell(cell);

            // Printed Date cell
            phrase = new Phrase();
            phrase.add(new Chunk(dateFormat.format(new Timestamp(System.currentTimeMillis())), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(cell);

            // Page Number cell
            phrase = new Phrase();
            phrase.add(new Chunk(String.format("Page %d of ", writer.getPageNumber()), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(cell);

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setPaddingTop(1f); 
            totalPageCount.setPaddingLeft(2f); 
            footer.addCell(totalPageCount);

            footer.writeSelectedRows(0, -1, 20, 34, writer.getDirectContent()); 
        }
         
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            
            template = writer.getDirectContent().createTemplate(30, 16);
            
            try {
                
                total = Image.getInstance(template);
                total.setRole(PdfName.ARTIFACT);
                
            } catch (DocumentException de) {
                
                throw new ExceptionConverter(de);
            }
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(template, Element.ALIGN_RIGHT, 
                                       new Phrase(String.valueOf(writer.getPageNumber()), 
                                       new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)), 
                                       totalWidth, 6, 0);
        } 
    }
    
    public class HeaderTableInterface extends PdfPageEventHelper {

        PdfTemplate numberOfPages;

        protected PdfPTable header;
        protected float tableHeight;
        protected Interface iface;

        public HeaderTableInterface(Interface iface) {

            this.iface = iface;
        }
        
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {

            numberOfPages = writer.getDirectContent().createTemplate(30, 16);
        }

        public float getTableHeight() {

            return tableHeight;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            
            if (writer.getPageNumber() > 1) {
            
                document.setMargins(PDFConstants.MARGIN_LEFT_DEFAULT,
                                    PDFConstants.MARGIN_RIGHT_DEFAULT,
                                    PDFConstants.MARGIN_TOP_DEFAULT,
                                    PDFConstants.MARGIN_BOTTOM_DEFAULT);
            }
        }
      
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            if (writer.getPageNumber() == 1) {

                try {

                    addFrontHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {

                    addBodyHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void addFrontHeaderTable(PdfWriter writer, Document document) throws Exception {

            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(iface.getProject().getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Blank Cell Row
            phrase = new Phrase();
            phrase.add(new Chunk(" ", PDFConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Document Title
            phrase = new Phrase();
            phrase.add(new Chunk("INTERFACE STATUS REPORT", PDFConstants.SUBSUBTITLE));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setPaddingTop(5);
            cell.setPaddingBottom(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.BOX);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());
        }

        private void addBodyHeaderTable(PdfWriter writer, Document document) throws Exception {

            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(iface.getProject().getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());

            // Horizontal Line below Page Header
    //        PdfContentByte canvas = writer.getDirectContent();
    //        canvas.moveTo(20, document.top() + 6);
    //        canvas.lineTo(575, document.top() + 6);
    //        canvas.setLineWidth(2f); 
    //        canvas.stroke();
        }    
    }
    
    public class FooterTableInterface extends PdfPageEventHelper {
         
        protected PdfPTable footer;
        protected Document document;
        protected Interface iface;
        protected PdfTemplate template;
        protected Image total;

        public FooterTableInterface(Document document, Interface iface) {

            this.document = document;
            this.iface = iface;
        }
         
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            PdfPCell cell;
            Phrase phrase;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String projectName = iface.getProject().getProjectName();

            float[] colWidths = {11, 7, 8, 1};
            footer = new PdfPTable(colWidths);
            footer.setTotalWidth(555);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // Project Name cell
            cell = new PdfPCell(new Phrase(projectName, PDFConstants.TABLE_CELL_SMALL));
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            footer.addCell(cell);

            // Printed Date cell
            phrase = new Phrase();
            phrase.add(new Chunk(dateFormat.format(new Timestamp(System.currentTimeMillis())), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(cell);

            // Page Number cell
            phrase = new Phrase();
            phrase.add(new Chunk(String.format("Page %d of ", writer.getPageNumber()), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(cell);

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setPaddingTop(1f); 
            totalPageCount.setPaddingLeft(2f); 
            footer.addCell(totalPageCount);

            footer.writeSelectedRows(0, -1, 20, 34, writer.getDirectContent()); 
        }
        
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            
            template = writer.getDirectContent().createTemplate(30, 16);
            
            try {
                
                total = Image.getInstance(template);
                total.setRole(PdfName.ARTIFACT);
                
            } catch (DocumentException de) {
                
                throw new ExceptionConverter(de);
            }
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(template, Element.ALIGN_RIGHT, 
                                       new Phrase(String.valueOf(writer.getPageNumber()), 
                                       new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)), 
                                       totalWidth, 6, 0);
        }
    }

    public class HeaderTableSystem extends PdfPageEventHelper {
       
        PdfTemplate numberOfPages;

        protected PdfPTable header;
        protected float tableHeight;
        protected Project project; 
        
        public HeaderTableSystem(Project project) {

            this.project = project;
        }
     
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            
            if (writer.getPageNumber() > 1) {
            
                document.setMargins(PDFConstants.MARGIN_LEFT_DEFAULT,
                                    PDFConstants.MARGIN_RIGHT_DEFAULT,
                                    PDFConstants.MARGIN_TOP_DEFAULT,
                                    PDFConstants.MARGIN_BOTTOM_DEFAULT);
            }
        }
    
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            
            if (writer.getPageNumber() == 1) {

                try {

                    addFrontHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {

                    addBodyHeaderTable(writer, document);

                } catch (Exception ex) {

                    Logger.getLogger(ReportsPDFServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void addFrontHeaderTable(PdfWriter writer, Document document) throws Exception {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            header.setTotalWidth(555);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(project.getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Blank Cell Row
            phrase = new Phrase();
            phrase.add(new Chunk(" ", PDFConstants.TABLE_CELL));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            header.addCell(cell);

            // Document Title
            phrase = new Phrase();
            phrase.add(new Chunk("Riyadh Metro M3", PDFConstants.SUBSUBTITLE));
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setPaddingTop(5);
            cell.setPaddingBottom(8);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2); 
            header.addCell(cell);

            // Document Date
            phrase = new Phrase();
            phrase.add(new Chunk(dateFormat.format(new Timestamp(System.currentTimeMillis())), PDFConstants.SUBSUBTITLE));
            cell = new PdfPCell(phrase);
            cell.setPaddingTop(5);
            cell.setPaddingBottom(8);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());
        }

        private void addBodyHeaderTable(PdfWriter writer, Document document) throws Exception {

            float width = writer.getPageSize().getWidth();
            float[] colWidths = {1, 2, 1};
            header = new PdfPTable(colWidths);
            if (width < 1000.0) {           
               header.setTotalWidth(555);   // A4 Portarit or A4 Landscape
            } else {                        
                header.setTotalWidth(1150); // A3 Landscape
            }
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell;
            Phrase phrase;

            // PMCM Logo cell
            Image pmcmLogo = Image.getInstance(PDFConstants.DEFUALT_PMCM_IMAGE);
            pmcmLogo.setAlignment(Image.ALIGN_LEFT);
            pmcmLogo.scaleToFit(140f, 28f);
            cell = new PdfPCell(pmcmLogo);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            // Project Name cell
            phrase = new Phrase();
            phrase.add(new Chunk(project.getProjectName().toUpperCase(), PDFConstants.TABLE_CELL_BROWN_TITLE));
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            cell.setFixedHeight(24);
            header.addCell(cell);

            // Contractor Logo cell
            Image contractorLogo = Image.getInstance(PDFConstants.DEFUALT_CONTRACTOR_IMAGE);
            contractorLogo.setAlignment(Image.ALIGN_RIGHT);
            contractorLogo.scaleToFit(100f, 28f);
            cell = new PdfPCell(contractorLogo);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingBottom(6f);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2f); 
            header.addCell(cell);

            tableHeight = header.getTotalHeight();

            header.writeSelectedRows(0, -1, 20, document.top() + ((document.topMargin() + tableHeight) / 2), writer.getDirectContent());
        }    
    }
   
    public class FooterTableSystem extends PdfPageEventHelper {
         
        protected PdfPTable footer;
        protected Document document;
        protected Project project;
        protected PdfTemplate template;
        protected Image total;

        public FooterTableSystem(Document document, Project project) {

            this.document = document;
            this.project = project;
        }
         
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            PdfPCell cell;
            Phrase phrase;
            float width = writer.getPageSize().getWidth();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String projectName = project.getProjectName();

            if (width < 1000.0) {           
                float[] colWidths = {11, 11, 10, 1};
                footer = new PdfPTable(colWidths);
                footer.setTotalWidth(555);   // A4 Portarit or A4 Landscape
            } else {    
                float[] colWidths = {22, 22, 21, 1};
                footer = new PdfPTable(colWidths);
                footer.setTotalWidth(1150); // A3 Landscape
            }
            footer.setLockedWidth(true);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // Project Name cell
            cell = new PdfPCell(new Phrase(projectName, PDFConstants.TABLE_CELL_SMALL));
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            footer.addCell(cell);

            // Printed Date cell
            phrase = new Phrase();
            phrase.add(new Chunk(dateFormat.format(new Timestamp(System.currentTimeMillis())), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(cell);

            // Page Number cell
            phrase = new Phrase();
            phrase.add(new Chunk(String.format("Page %d of ", writer.getPageNumber()), PDFConstants.TABLE_CELL_SMALL)); 
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(BaseColor.GRAY);
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(cell);

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.GRAY);
            totalPageCount.setBorderWidth(2);
            totalPageCount.setPaddingTop(1f); 
            totalPageCount.setPaddingLeft(2f); 
            footer.addCell(totalPageCount);

            footer.writeSelectedRows(0, -1, 20, 34, writer.getDirectContent()); 
        }
        
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            
            template = writer.getDirectContent().createTemplate(30, 16);
            
            try {
                
                total = Image.getInstance(template);
                total.setRole(PdfName.ARTIFACT);
                
            } catch (DocumentException de) {
                
                throw new ExceptionConverter(de);
            }
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(template, Element.ALIGN_RIGHT, 
                                       new Phrase(String.valueOf(writer.getPageNumber()), 
                                       new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)), 
                                       totalWidth, 6, 0);
        }
    }
 
    public class TableOfContents extends PdfPageEventHelper {

        protected List<AbstractMap.SimpleEntry<String, Integer>> toc = new ArrayList<>();

        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {

            toc.add(new AbstractMap.SimpleEntry(text, writer.getPageNumber()));
        }

        @SuppressWarnings("rawtypes")
        public List getTOC() {

            return toc;
        }
    }
}
