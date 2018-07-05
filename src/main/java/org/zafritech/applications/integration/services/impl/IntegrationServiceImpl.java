/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.dao.ElementCreateDao;
import org.zafritech.applications.integration.data.dao.ElementEditDao;
import org.zafritech.applications.integration.data.dao.SBSTreeDao;
import org.zafritech.applications.integration.data.dao.TreeElementDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.services.IntegrationService;
import org.zafritech.core.data.dao.generic.TriValueDao;
import org.zafritech.core.data.dao.generic.ValuePairDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author lukes
 */
@Service
public class IntegrationServiceImpl implements IntegrationService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private UserSessionService userSessionService;
	
    @Autowired
    private IntegrationEntityRepository integrationEntityRepository;
	
    @Autowired
    private ElementRepository elementRepository;
   
    @Autowired
    private InterfaceIssueRepository issueRepository;
   
    @Autowired
    private InterfaceRepository interfaceRepository;
   
    @Override
    public IntegrationEntity createIntegrationEntity(ValuePairDao dao) {
        
        Project project = projectRepository.findOne(dao.getId());
        Company company = companyRepository.findOne(Long.valueOf(dao.getItemName()));
        String displayCode = dao.getItemName();
                
        IntegrationEntity entity = new IntegrationEntity(project, company, displayCode);
        entity = integrationEntityRepository.save(entity);
                
        return entity;
    }
    
    @Override
    public List<SBSTreeDao> getSBSTree(Project project) {
        
        List<SBSTreeDao> sbsTree = new ArrayList<>();
        List<IntegrationEntity> entities = integrationEntityRepository.findByProjectAndHasElementsOrderBySortOrderAsc(project, true);
     
        // Root Element (Project)
        SBSTreeDao rootItem = new SBSTreeDao(1L, 0L, project.getProjectName(), true, true, false, "/images/icons/db-icon.png", project.getId());
        sbsTree.add(rootItem);
        
        if (entities.size() > 0) {
            
            Long level_1_id = 2L;

            for (IntegrationEntity entity : entities) {

                SBSTreeDao entityItem = new SBSTreeDao(level_1_id, 
                                                       1L, 
                                                       entity.getSbs() + " " + entity.getCompany().getCompanyCode() + " - " + entity.getCompany().getCompanyName(), 
                                                       true, 
                                                       true, 
                                                       true, 
                                                       "/images/icons/chart-organisation-icon.png", 
                                                       entity.getId());

                sbsTree.add(entityItem);

                List<TreeElementDao> treeItems = getElementsTree(entity);

                Long level_2_id = (level_1_id * 1000) + 1;

                for (TreeElementDao item : treeItems) {

                    List<Element> children = elementRepository.findByParentOrderBySortOrder(item.getElement());

                    SBSTreeDao element = new SBSTreeDao(level_2_id, 
                                                        level_1_id, 
                                                        item.getElement().getSbs() + " " + item.getElement().getName(),
                                                        true,
                                                        !children.isEmpty(),
                                                        true,
                                                        item.getElement().getId());

                    sbsTree.add(element);

                    if (!children.isEmpty()) {

                        Long leafId = (level_2_id * 1000) + 1;

                        for (Element child : children) {

                            SBSTreeDao leaf = new SBSTreeDao(leafId, 
                                                             level_2_id, 
                                                             child.getSbs() + " " + child.getName(),
                                                             true,
                                                             false,
                                                             true,
                                                             child.getId());

                            sbsTree.add(leaf);

                            leafId++;
                        }
                    }

                    level_2_id++;
                }

                level_1_id++;
            }
            
            return sbsTree;
        }
        
        return sbsTree;
    }
    
    @Override
    public List<TreeElementDao> getElementsTree() {
        
        List<TreeElementDao> elementsTree = new ArrayList<>();
        
        List<Element> elements = elementRepository.findByParent(null); 
        
        elements.stream().map((element) -> {
            
            TreeElementDao dao = new TreeElementDao(element);
            List<Element> children = elementRepository.findByParent(element);
            if (!children.isEmpty()) dao.setChildren(children);
            return dao;
            
        }).forEachOrdered((dao) -> {
            
            elementsTree.add(dao);
        });
        
        return elementsTree;
    }

    @Override
    public List<TreeElementDao> getElementsTree(IntegrationEntity entity) {
 
        List<TreeElementDao> elementsTree = new ArrayList<>();
            
        List<Element> elements = elementRepository.findByEntityAndParentOrderBySortOrder(entity, null); 
        
        elements.stream().map((element) -> {
            
            TreeElementDao dao = new TreeElementDao(element);
            List<Element> children = elementRepository.findByParent(element);
            if (!children.isEmpty()) dao.setChildren(children);
            return dao;
            
        }).forEachOrdered((dao) -> {
            
            elementsTree.add(dao);
        });
        
        return elementsTree;
    }

    @Override
    public List<TreeElementDao> getElementsBubTreeByParent(Element parent) {
        
        List<TreeElementDao> elementsTree = new ArrayList<>();
        
        List<Element> elements = elementRepository.findByParent(parent);
        
        elements.stream().map((element) -> {
            
            TreeElementDao dao = new TreeElementDao(element);
            List<Element> children = elementRepository.findByParent(element);
            if (!children.isEmpty()) dao.setChildren(children);
            return dao;
            
        }).forEachOrdered((dao) -> {
            
            elementsTree.add(dao);
        });
        
        return elementsTree;
    }

    @Override
    public Element createNewElement(ElementCreateDao dao) {
        
        Project project = userSessionService.getLastOpenProject();
        
        Element element = new Element(project,
                                      dao.getSbs(),
                                      dao.getName(),
                                      integrationEntityRepository.findOne(dao.getEntityId()),
                                      dao.getParentId() != null ? elementRepository.findOne(dao.getParentId()) : null,
                                      !dao.getElementDescription().isEmpty() ? dao.getElementDescription() : null);
        
        element = elementRepository.save(element);
        
        return element;
    }

    @Override
    public Element updateElement(ElementEditDao dao) {
        
        Element element = elementRepository.findOne(dao.getId());
        Element parent = dao.getParentId() != null ? elementRepository.findOne(dao.getParentId()) : null;
        
        element.setParent(parent);
        element.setEntity(integrationEntityRepository.findOne(dao.getEntityId()));
        element.setSbs(dao.getSbs());
        element.setName(dao.getName());
        
        if(dao.getElementDescription() != null && !dao.getElementDescription().isEmpty()) {
            
            element.setDescription(dao.getElementDescription());
        }
        
        element = elementRepository.save(element);
        
        return element;
    }
    
    @Override
    public InterfaceIssue createNewInterfaceIssue(TriValueDao issueDao) {
        
        String issueTitle = issueDao.getItemName();
        String issueDescription = issueDao.getItemDescription();
        
        if((issueTitle == null || issueTitle.isEmpty()) ||
           (issueDescription == null || issueDescription.isEmpty())) {
            
            return null;
        }
        
        InterfaceIssue issue = new InterfaceIssue(getNextIssueIdentifier(),
                                                  interfaceRepository.findOne(issueDao.getId()), 
                                                  issueTitle,
                                                  issueDescription);
        
        issue = issueRepository.save(issue);
        
        return issue;
    }
    
    @Override
    public String getNextIssueIdentifier() {
        
        String identifier = "ISS";
        String format = "%06d";
        Integer next = 1;
        
        InterfaceIssue last = issueRepository.findFirstByOrderByIdDesc();
         
        if (last != null) {

            Integer length = last.getSystemId().length();
            String lastNumeric = last.getSystemId().substring(length - 6);
            next = Integer.parseInt(lastNumeric) + 1;
        }
        
        identifier = identifier + String.format(format, next);
        
        return identifier;
    }
}
