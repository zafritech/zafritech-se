/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import java.util.List;

import org.zafritech.applications.integration.data.dao.ElementCreateDao;
import org.zafritech.applications.integration.data.dao.ElementEditDao;
import org.zafritech.applications.integration.data.dao.SBSTreeDao;
import org.zafritech.applications.integration.data.dao.TreeElementDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.core.data.dao.generic.TriValueDao;
import org.zafritech.core.data.dao.generic.ValuePairDao;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author lukes
 */
public interface IntegrationService {
    
    public IntegrationEntity createIntegrationEntity(ValuePairDao dao);
            
    public List<SBSTreeDao> getSBSTree(Project project);
    
    public List<TreeElementDao> getElementsTree();
    
    public List<TreeElementDao> getElementsTreeByEntity(IntegrationEntity entity);
    
    public List<TreeElementDao> getElementsTreeByParent(Element parent);
    
    public Element createNewElement(ElementCreateDao dao);
    
    public Element updateElement(ElementEditDao dao);
    
    public InterfaceIssue createNewInterfaceIssue(TriValueDao issueDao);

    public String getNextIssueIdentifier();
}
