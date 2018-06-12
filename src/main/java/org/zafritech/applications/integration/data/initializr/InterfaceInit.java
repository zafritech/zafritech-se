/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.applications.integration.data.dao.InterfaceDao;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.applications.integration.services.InterfaceService;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;

/**
 *
 * @author lukes
 */
@Component
public class InterfaceInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private InterfaceRepository interfaceRepository;
    
    @Autowired
    private ElementRepository elementRepository;
    
    @Autowired
    private InterfaceService interfaceService;
    
    @Autowired
    private IntegrationEntityRepository integrationEntityRepository;
    
    @Autowired
    private ProjectCompanyRoleRepository projectCompanyRoleRepository;
    
    @Transactional
    public void init() {
        
        String dataFile = data_dir + "initialisation/integration-interfaces.json";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<InterfaceDao> json = Arrays.asList(mapper.readValue(new File(dataFile), InterfaceDao[].class));
            
            for (InterfaceDao dao : json) {
            
                IntegrationEntity primary = integrationEntityRepository.findByCompanyCompanyCode(dao.getPrimaryEntity());
                IntegrationEntity secondary = integrationEntityRepository.findByCompanyCompanyCode(dao.getSecondaryEntity());
                Project project = primary.getProject();
                String systemId = interfaceService.getNextSystemIdentifier(primary, secondary);
                InterfaceStatus status = (dao.isOpenStatus() ? InterfaceStatus.INTERFACE_STATUS_OPEN : InterfaceStatus.INTERFACE_STATUS_CLOSED);
                
                Interface interf = new Interface(systemId,
                                                 project,
                                                 elementRepository.findBySbs(dao.getPrimaryElement()),
                                                 elementRepository.findBySbs(dao.getSecondaryElement()),
                                                 integrationEntityRepository.findByCompanyCompanyCode(dao.getPrimaryEntity()),
                                                 integrationEntityRepository.findByCompanyCompanyCode(dao.getSecondaryEntity()),
                                                 dao.getInterfaceLevel(),
                                                 dao.getInterfaceTitle(),
                                                 dao.getInterfaceDescription());
                
                interf.setStatus(status);  
                
                interfaceRepository.save(interf);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(InterfaceInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
