/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.ElementViewDao;
import org.zafritech.applications.integration.data.domain.Element;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class ElementToElementViewDaoConverter implements Converter<Element, ElementViewDao> {

    @Override
    public ElementViewDao convert(Element element) {
        
        ElementViewDao elemmentDao = new ElementViewDao();
        
        elemmentDao.setId(element.getId());
        elemmentDao.setProjectId(element.getProject().getId());
        elemmentDao.setEntityId(element.getEntity().getId());
        elemmentDao.setParentId(element.getParent() != null ? element.getParent().getId() : null);
        elemmentDao.setSbs(element.getSbs());
        elemmentDao.setName(element.getName());
        elemmentDao.setDescription(element.getDescription());
        
        return elemmentDao;
    }
}
