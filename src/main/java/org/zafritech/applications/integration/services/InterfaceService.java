/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zafritech.applications.integration.data.dao.InterfaceCommentDao;
import org.zafritech.applications.integration.data.dao.InterfaceCommentUpdateDao;
import org.zafritech.applications.integration.data.dao.InterfaceCreateDao;
import org.zafritech.applications.integration.data.dao.InterfaceEditDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssueComment;

/**
 *
 * @author lukes
 */
public interface InterfaceService {
    
    Interface saveInterface(InterfaceCreateDao interfaceDao);
    
    Interface updateInterface(InterfaceEditDao interfaceDao);
    
    InterfaceIssueComment createNewInterfaceIssueComment(InterfaceCommentDao commentDao);
    
    InterfaceIssueComment updateInterfaceIssueComment(InterfaceCommentUpdateDao dao);
    
    List<Interface> findByLevels(Element element, String levels);
    
    boolean isSelfInterfacing(InterfaceCreateDao interfaceDao);
    
    boolean isSelfInterfacing(InterfaceEditDao interfaceDao);
    
    boolean isDuplicateInterface(InterfaceCreateDao interfaceDao);
    
    XSSFWorkbook DownloadExcel()  throws FileNotFoundException, IOException;
    
    String getNextSystemIdentifier(IntegrationEntity primary, IntegrationEntity secondary);
}
