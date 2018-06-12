/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.services;

import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.RunOnceTask;

/**
 *
 * @author lukes
 */
@Service
public interface IntegrationInitService {
    
    RunOnceTask initIntegrationElements();
    
    RunOnceTask initInterfaces();
    
    RunOnceTask initInterfaceTypes();
}
