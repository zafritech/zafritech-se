package org.zafritech.applications.requirements.data.initializr;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.repositories.ApplicationRepository;

@Component
public class RequirementsInit {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Transactional
    public void init() {
    	
    	Application appRequirements = new Application("requirements", "Requirements Management");
        appRequirements.setApplicationDescription("The Requirements management application enables the management of requirements for a system inline with the V-Model.");
        appRequirements.setPublished(false);
        appRequirements.setFaIcon("fa-crosshairs");
        
    	applicationRepository.save(appRequirements);
    }
}
