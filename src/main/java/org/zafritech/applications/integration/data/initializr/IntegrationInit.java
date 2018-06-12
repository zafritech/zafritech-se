package org.zafritech.applications.integration.data.initializr;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.repositories.ApplicationRepository;

@Component
public class IntegrationInit {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Transactional
    public void init() {
    	
    	Application appIntegration = new Application("integration", "Systems Integration");
        appIntegration.setApplicationDescription("Systems Integration application enables the management of interfaces between different interfacing entities of a system."); 
        
    	applicationRepository.save(appIntegration);
    }
}
