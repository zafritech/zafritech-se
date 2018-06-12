/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
@Service
public interface ReportsPDFService {
    
    byte[] getIssuePDFStatusReport(InterfaceIssue issue) throws Exception;
    
    byte[] getInterfacePDFStatusReport(Interface iface) throws Exception;
    
    byte[] getSystemPDFStatusReport(Project project) throws Exception;
}
