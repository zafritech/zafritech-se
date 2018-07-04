/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import com.itextpdf.text.Document;
import java.util.AbstractMap;
import java.util.List;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
public interface SystemPDFReportService {
   
    public void pdfAddFrontPage(Document document, Project project) throws Exception;
    
    public void pdfAddTableOfContents(Document document,List<AbstractMap.SimpleEntry<String, Integer>> entries) throws Exception;
    
    public void pdfAddSBSPage(Document document, Project project) throws Exception;
    
    public void pdfAddInterfacesStatus(Document document, Project project) throws Exception;
    
    public void pdfAddInterfaceMatrix(Document document, Project project) throws Exception;
    
    public void pdfIntegrationVerification(Document document, Project project)  throws Exception;
}
