/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

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
import org.zafritech.applications.integration.data.dao.ProjectCompanyRoleDao;
import org.zafritech.applications.integration.data.initializr.InterfaceInit;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectCompanyRole;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.enums.CompanyRole;

/**
 *
 * @author Luke Sibisi
 */
@Component
public class ProjectCompanyRolesInit {
  
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
  
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private ProjectCompanyRoleRepository roleRepository;
    
    @Transactional
    public void init() {
     
        String dataFile = data_dir + "initialisation/integration-entities.json";
        
        Project project = projectRepository.findFirstByProjectShortName("Riyadh Metro M3");
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<ProjectCompanyRoleDao> json = Arrays.asList(mapper.readValue(new File(dataFile), ProjectCompanyRoleDao[].class));
            
            json.forEach((_item) -> {

                Company company = new Company(_item.getName(), _item.getCode());
                company.setCompanyCode( _item.getCode()); 
                company.setCompanyShortName( _item.getShortName()); 
                company.setCompanyRole(CompanyRole.valueOf( _item.getEntityRole())); 
                company = companyRepository.save(company);
                
                ProjectCompanyRole role = new ProjectCompanyRole(project, 
                                                                 company, 
                                                                 company.getCompanyRole(),
                                                                 _item.getDisplayCode());
                role.setDiplayCode(_item.getDisplayCode()); 
                role.setIsActive(true); 
                role.setCompanyRoleDescription("System generated project company role"); 
                roleRepository.save(role);
                
            });
            
            
        } catch (IOException ex) {
            
            Logger.getLogger(InterfaceInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
