/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import java.util.List;
import javax.persistence.Entity;
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
public class EntityToEntityViewDaoConverter implements Converter<IntegrationEntity, EntityViewDao> {

    @Autowired
    InterfaceRepository interfaceRepository;

    @Override
    public EntityViewDao convert(IntegrationEntity entity) {
        
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
    }
}
