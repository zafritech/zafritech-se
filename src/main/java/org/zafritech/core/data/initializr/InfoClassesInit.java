/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.repositories.InformationClassRepository;

/**
 *
 * @author LukeS
 */
@Component
public class InfoClassesInit {
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Transactional
    public void init() {
        
        infoClassRepository.save(new InformationClass("Unclassified", "INFO_UNCLASSIFIED"));
        infoClassRepository.save(new InformationClass("Open Source", "INFO_OPEN"));
        infoClassRepository.save(new InformationClass("Official", "INFO_OFFICIAL"));
        infoClassRepository.save(new InformationClass("Confidential", "INFO_CONFIDENTIAL"));
        infoClassRepository.save(new InformationClass("Restricted", "INFO_RESTRICTED"));
        infoClassRepository.save(new InformationClass("Secret", "INFO_SECRET"));
    }
}
