/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.components.IntegrationPDFClasses;
import org.zafritech.applications.integration.components.IntegrationPDFClasses.FooterTableIssue;
import org.zafritech.applications.integration.components.IntegrationPDFClasses.HeaderTableInterface;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.constants.PDFConstants;
import org.zafritech.applications.integration.services.InterfacesPDFService;
import org.zafritech.applications.integration.services.IssuesPDFService;
import org.zafritech.applications.integration.services.ReportsPDFService;
import org.zafritech.applications.integration.services.SystemPDFReportService;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
@Service
public class ReportsPDFServiceImpl implements ReportsPDFService {

    @Autowired
    private SystemPDFReportService systemPDFService;

    @Autowired
    private InterfacesPDFService interfacesPDFService;

    @Autowired
    private IssuesPDFService issuesPDFService;
 
    @Override
    public byte[] getIssuePDFStatusReport(InterfaceIssue issue) throws Exception {
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IntegrationPDFClasses pdfClasses = new IntegrationPDFClasses();
        
        Document document = new Document(PageSize.A4,
                                         PDFConstants.MARGIN_LEFT_DEFAULT,
                                         PDFConstants.MARGIN_RIGHT_DEFAULT,
                                         PDFConstants.MARGIN_TOP_FIRST_PAGE_SMALL,
                                         PDFConstants.MARGIN_BOTTOM_DEFAULT);
       
        PdfWriter writer = PdfWriter.getInstance(document, os);
        
        // Document Header Event
        IntegrationPDFClasses.HeaderTableIssue headerEvent = pdfClasses.new HeaderTableIssue(issue);
        writer.setPageEvent(headerEvent);
        
        // Document Footer Event
        IntegrationPDFClasses.FooterTableIssue footerEvent = pdfClasses.new FooterTableIssue(document, issue);
        writer.setPageEvent(footerEvent);
        
        document.open();
        
        // Set Margins for Pages >  1;
        document.setMargins(PDFConstants.MARGIN_LEFT_DEFAULT,
                            PDFConstants.MARGIN_RIGHT_DEFAULT,
                            PDFConstants.MARGIN_TOP_DEFAULT,
                            PDFConstants.MARGIN_BOTTOM_DEFAULT);
        
        issuesPDFService.pdfAddInterfaceIssue(document, issue);
        issuesPDFService.pdfAddIssueComments(document, issue);
        
        // Close PDF document
        document.close();

        // Stream out PDF as byte
        // Stream out PDF as byte array
        byte[] pdfAsBytes = os.toByteArray();

        return pdfAsBytes;
    }
    
    @Override
    public byte[] getInterfacePDFStatusReport(Interface iface) throws Exception {
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IntegrationPDFClasses pdfClasses = new IntegrationPDFClasses();
        
        Document document = new Document(PageSize.A4,
                                         PDFConstants.MARGIN_LEFT_DEFAULT,
                                         PDFConstants.MARGIN_RIGHT_DEFAULT,
                                         PDFConstants.MARGIN_TOP_FIRST_PAGE_SMALL,
                                         PDFConstants.MARGIN_BOTTOM_DEFAULT);
       
        PdfWriter writer = PdfWriter.getInstance(document, os);

        // Document Header Event
        IntegrationPDFClasses.HeaderTableInterface headerEvent = pdfClasses.new HeaderTableInterface(iface);
        writer.setPageEvent(headerEvent);
        
        // Document Footer Event
        IntegrationPDFClasses.FooterTableInterface footerEvent = pdfClasses.new FooterTableInterface(document, iface);
        writer.setPageEvent(footerEvent);
        
        document.open();
        
        // Set Margins for Pages >  1;
        document.setMargins(PDFConstants.MARGIN_LEFT_DEFAULT,
                            PDFConstants.MARGIN_RIGHT_DEFAULT,
                            PDFConstants.MARGIN_TOP_DEFAULT,
                            PDFConstants.MARGIN_BOTTOM_DEFAULT);
        
        interfacesPDFService.pdfAddInterfaceDetails(document, iface);
        interfacesPDFService.pdfAddInterfaceIssuesTitle(document, iface);
        
        List<InterfaceIssue> issues = iface.getIssues();
        
        if (issues.size() > 0) {
            
            interfacesPDFService.pdfAddInterfaceIssues(document, iface);
            
        } else {
            
            interfacesPDFService.pdfAddInterfaceNoIssues(document, iface);
        }
        
        // Close PDF document
        document.close();

        // Stream out PDF as byte array
        byte[] pdfAsBytes = os.toByteArray();

        return pdfAsBytes;
    }

    @Override
    public byte[] getSystemPDFStatusReport(Project project) throws Exception {
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IntegrationPDFClasses pdfClasses = new IntegrationPDFClasses();
        
        Document document = new Document(PageSize.A4,
                                         PDFConstants.MARGIN_LEFT_DEFAULT,
                                         PDFConstants.MARGIN_RIGHT_DEFAULT,
                                         PDFConstants.MARGIN_TOP_FIRST_PAGE_SMALL,
                                         PDFConstants.MARGIN_BOTTOM_DEFAULT);
       
        PdfWriter writer = PdfWriter.getInstance(document, os);
 
        // Document Header Event
        IntegrationPDFClasses.HeaderTableSystem headerEvent = pdfClasses.new HeaderTableSystem(project);
        writer.setPageEvent(headerEvent);
        
        // Document Footer Event
        IntegrationPDFClasses.FooterTableSystem footerEvent = pdfClasses.new FooterTableSystem(document, project);
        writer.setPageEvent(footerEvent);
        
        // TOC Footer Event
        IntegrationPDFClasses.TableOfContents tocEvent = pdfClasses.new TableOfContents();
        writer.setPageEvent(tocEvent);
        
        document.open();
        
        // Front Page (Title Page)
        systemPDFService.pdfAddFrontPage(document, project); 
        
        // Table of Contents
        List<AbstractMap.SimpleEntry<String, Integer>> entries = tocEvent.getTOC();
        systemPDFService.pdfAddTableOfContents(document, entries);
        
        // System Breakdown Structure
        systemPDFService.pdfAddSBSPage(document, project); 
        
        // Change page to A3 Landscape for the next pages
        document.setPageSize(PageSize.A3.rotate());
        
        // Interface Matrix 
        systemPDFService.pdfAddInterfaceMatrix(document, project);
        
        // Close PDF document
        document.close();
 
        // Stream out PDF as byte array
        byte[] pdfAsBytes = os.toByteArray();

        return pdfAsBytes;
    }
}
