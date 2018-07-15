/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.VerificationViewDao;
import org.zafritech.applications.integration.data.domain.IntegrationVerification;

/**
 *
 * @author LukeS
 */
@Component
public class VerificationToVerificationVewDaoConverter implements Converter<IntegrationVerification, VerificationViewDao> {
    
    @Override
    public VerificationViewDao convert(IntegrationVerification verification) {
 
        VerificationViewDao dao = new VerificationViewDao();
        
        dao.setId(verification.getId());
        dao.setUuId(verification.getUuId());
        dao.setSystemId(verification.getSystemId());
        dao.setProject(verification.getProject() != null ? verification.getProject().getProjectName() : null);
        dao.setTitle(verification.getTitle());
        dao.setTitle(verification.getTitle());
        dao.setDescription(verification.getDescription());
        dao.setMethod(verification.getMethod() != null ? verification.getMethod().getEntityTypeName() : null);
        dao.setReference(verification.getReference() != null ? verification.getReference().getNumber() : null);
        dao.setStatus(verification.getVerificationStatus() != null ? verification.getVerificationStatus().name() : null);
        
        return dao;
    }
}
