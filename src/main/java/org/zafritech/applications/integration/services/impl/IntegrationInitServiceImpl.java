/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services.impl;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.applications.integration.data.initializr.ElementInit;
import org.zafritech.applications.integration.data.initializr.InterfaceInit;
import org.zafritech.applications.integration.data.initializr.InterfaceTypesInit;
import org.zafritech.applications.integration.services.IntegrationInitService;
import org.zafritech.core.data.domain.RunOnceTask;
import org.zafritech.core.data.repositories.RunOnceTaskRepository;
import org.zafritech.core.services.InitService;

/**
 *
 * @author lukes
 */
@Service
public class IntegrationInitServiceImpl implements IntegrationInitService{

    @Autowired
    private RunOnceTaskRepository runOnceTaskRepository;
    
    @Autowired
    private ElementInit elementInit;
    
    @Autowired
    private InterfaceInit interfaceInit;
    
    @Autowired
    private InterfaceTypesInit interfaceTypesInit;
    
    @Override
    public RunOnceTask initIntegrationElements() {
        
        if (!isInitComplete("INTEGRATION_ELEMENTS_INIT")) {
            
            elementInit.init();
            return completeTask("INTEGRATION_ELEMENTS_INIT");
        }
        
        return null;
    }
 
    @Override
    public RunOnceTask initInterfaces() {

        if (!isInitComplete("INTEGRATION_INTERFACES_INIT")) {
            
            interfaceInit.init();
            return completeTask("INTEGRATION_INTERFACES_INIT");
        }
        
        return null;
    }   
 
    @Override
    public RunOnceTask initInterfaceTypes() {
        
        if (!isInitComplete("INTEGRATION_INTERFACE_TYPES_INIT")) {
            
            interfaceTypesInit.init();
            return completeTask("INTEGRATION_INTERFACE_TYPES_INIT");
        }
        
        return null;
    }
    
    private RunOnceTask completeTask(String taskName) {
        
        RunOnceTask task;

        task = runOnceTaskRepository.findByTaskName(taskName);

        if (task == null) {

            task = new RunOnceTask(taskName);
        }

        task.setCompleted(true);
        task.setCreationDate(new Timestamp(System.currentTimeMillis()));
        task.setCompletedDate(new Timestamp(System.currentTimeMillis()));

        // User log files for this
        String msg = "RunOnce Task " + taskName + " completed....";
        Logger.getLogger(InitService.class.getName()).log(Level.INFO, msg);
        
        return runOnceTaskRepository.save(task);
    }
    
    private boolean isInitComplete(String taskName) {
        
        RunOnceTask task = runOnceTaskRepository.findByTaskNameAndCompleted(taskName, true);
        
        return task != null;
    }
}
