/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.ElementViewDao;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class ElementToElementViewDaoConverter implements Converter<Element, ElementViewDao> {

    @Autowired
    InterfaceRepository interfaceRepository;

    @Autowired
    ElementRepository elementRepository;

    @Override
    public ElementViewDao convert(Element element) {
       
        Integer ifaceCount = 0;
        ElementViewDao elemmentDao = new ElementViewDao();
        
        elemmentDao.setId(element.getId());
        elemmentDao.setProjectId(element.getProject().getId());
        elemmentDao.setEntityId(element.getEntity().getId());
        elemmentDao.setParentId(element.getParent() != null ? element.getParent().getId() : null);
        elemmentDao.setSbs(element.getSbs());
        elemmentDao.setName(element.getName());
        elemmentDao.setDescription(element.getDescription());
        elemmentDao.setInterfaceCount(countInterfaces(element, ifaceCount));
        
        return elemmentDao;
    }
    
    private Integer countInterfaces(Element element, Integer ifaceCount) {
         
        ifaceCount += interfaceRepository.findByPrimaryElement(element).size();
        ifaceCount += interfaceRepository.findBySecondaryElement(element).size();

        List<Element> children = elementRepository.findByParentOrderBySortOrder(element);

        if (!children.isEmpty()) {
            
            for (Element child : children) {
            
                ifaceCount = countInterfaces(child, ifaceCount);
            }
        }
        
        return ifaceCount;
    }
}
