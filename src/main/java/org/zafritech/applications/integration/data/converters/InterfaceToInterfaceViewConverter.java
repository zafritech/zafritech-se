/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.InterfaceViewDao;
import org.zafritech.applications.integration.data.domain.Interface;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class InterfaceToInterfaceViewConverter implements Converter<Interface, InterfaceViewDao> {

    @Override
    public InterfaceViewDao convert(Interface iface) {
       
        if (iface.getPrimaryElement() != null && iface.getSecondaryElement() != null) {

            InterfaceViewDao interfaceDao = new InterfaceViewDao();

            interfaceDao.setId(iface.getId());
            interfaceDao.setSystemId(iface.getSystemId());
            interfaceDao.setProjectId(iface.getProject().getId());
            interfaceDao.setProjectName(iface.getProject().getProjectName());
            interfaceDao.setPrimaryEntityId(iface.getPrimaryEntity().getId());
            interfaceDao.setPrimaryEntityName(iface.getPrimaryEntity().getCompany().getCompanyName());
            interfaceDao.setPrimaryEntityCode(iface.getPrimaryEntity().getCompanyCode());
            interfaceDao.setSecondaryEntityId(iface.getSecondaryEntity().getId());
            interfaceDao.setSecondaryEntityName(iface.getSecondaryEntity().getCompany().getCompanyName());
            interfaceDao.setSecondaryEntityCode(iface.getSecondaryEntity().getCompanyCode());
            interfaceDao.setPrimaryElementId(iface.getPrimaryElement().getId());
            interfaceDao.setPrimaryElementSbs(iface.getPrimaryElement().getSbs());
            interfaceDao.setPrimaryElementName(iface.getPrimaryElement().getName());
            interfaceDao.setSecondaryElementId(iface.getSecondaryElement().getId());
            interfaceDao.setSecondaryElementSbs(iface.getSecondaryElement().getSbs());
            interfaceDao.setSecondaryElementName(iface.getSecondaryElement().getName());
            interfaceDao.setInterfaceLevel(iface.getInterfaceLevel());
            interfaceDao.setInterfaceTitle(iface.getInterfaceTitle());
            interfaceDao.setInterfaceDescription(iface.getInterfaceDescription());
            interfaceDao.setInterfaceNotes(iface.getInterfaceNotes());
            interfaceDao.setStatus(iface.getStatus().name());
            interfaceDao.setIssues(iface.getIssues().size());

            return interfaceDao;
            
        } else { return null; }
    }
}
