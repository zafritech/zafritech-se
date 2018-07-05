/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.EntityViewDao;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class EntityListToEntityViewListDaoConverter implements Converter<List<IntegrationEntity>, List<EntityViewDao>> {

    @Autowired
    InterfaceRepository interfaceRepository;

    @Override
    public List<EntityViewDao> convert(List<IntegrationEntity> entities) {
        
        List<EntityViewDao> entityViews = new ArrayList();
        
        entities.stream().map((entity) -> {

            Integer ifaceCount = interfaceRepository.findByPrimaryEntityAndSecondaryEntity(entity, entity).size();
            ifaceCount += interfaceRepository.findByPrimaryEntityAndSecondaryEntityNot(entity, entity).size();
            ifaceCount += interfaceRepository.findByPrimaryEntityNotAndSecondaryEntity(entity, entity).size();
            
            EntityViewDao entityDao = new EntityViewDao();
            entityDao.setId(entity.getId());
            entityDao.setProjectId(entity.getProject().getId());
            entityDao.setCompanyCode(entity.getCompanyCode()); 
            entityDao.setHasElements(entity.isHasElements());
            entityDao.setInterfaceCount(ifaceCount); 
            
            return entityDao;    
            
        }).forEachOrdered((entityDao) -> {
            
            entityViews.add(entityDao);
        });
        
        return entityViews;
    }
}
