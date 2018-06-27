/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.risksman.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.repositories.ApplicationRepository;

/**
 *
 * @author LukeS
 */
@Component
public class RiskmanInit {
  
    @Autowired
    private ApplicationRepository applicationRepository;
   
    @Transactional
    public void init() {
    	
    	Application appRisks = new Application("riskman", "Risk Management");
        appRisks.setApplicationDescription("The Risk management application enables the management of enginering risks for a system.");
        appRisks.setPublished(false);
        appRisks.setFaIcon("fa-exclamation-triangle");
        
    	applicationRepository.save(appRisks);
    }   
}
