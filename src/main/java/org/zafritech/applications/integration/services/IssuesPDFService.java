/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;

/**
 *
 * @author Luke Sibisi
 */
@Service
public interface IssuesPDFService {
   
    public void pdfAddInterfaceIssue(Document document, InterfaceIssue issue) throws DocumentException;
   
    public void pdfAddIssueComments(Document document, InterfaceIssue issue) throws DocumentException;
}
