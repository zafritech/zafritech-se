/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;

/**
 *
 * @author Luke Sibisi
 */
@Service
public interface InterfacesPDFService {
    
    public void pdfAddFrontPage(Document document, Interface iface) throws DocumentException;
    
    public void pdfAddInterfaceDetails(Document document, Interface iface) throws DocumentException;
    
    public void pdfAddInterfaceNoIssues(Document document, Interface iface) throws DocumentException;
    
    public void pdfAddInterfaceIssuesTitle(Document document, Interface iface) throws DocumentException;
    
    public void pdfAddInterfaceIssues(Document document, Interface iface) throws DocumentException;
    
    public void pdfAddInterfaceIssueComments(Document document, InterfaceIssue issue) throws DocumentException;
    
    public void pdfAddInterfaceIssueNoComments(Document document, InterfaceIssue issue) throws DocumentException;
            
    public PdfPCell setTableHeaderCellProperties(PdfPCell cell);
    
    public PdfPCell setTableHeaderGrayCellProperties(PdfPCell cell);
    
    public PdfPCell setTableHeaderLightGrayCellProperties(PdfPCell cell);
    
    public PdfPCell setTableCellProperties(PdfPCell cell);
    
    public void addEmptyLine(Paragraph paragraph, Integer lines);
}
