/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.repositories.RunOnceTaskRepository;
import org.zafritech.core.services.InitService;
import org.zafritech.applications.integration.data.initializr.IntegrationInit;
import org.zafritech.applications.integration.data.initializr.ElementInit;
import org.zafritech.applications.integration.data.initializr.InterfaceInit;
import org.zafritech.applications.integration.data.initializr.InterfaceTypesInit;
import org.zafritech.applications.requirements.data.initializr.RequirementsInit;
import org.zafritech.core.data.domain.RunOnceTask;
import org.zafritech.core.data.initializr.ClaimTypesInit;
import org.zafritech.core.data.initializr.CompaniesInit;
import org.zafritech.core.data.initializr.ContentDescriptorsInit;
import org.zafritech.core.data.initializr.CountriesDataInit;
import org.zafritech.core.data.initializr.DefinitionsInit;
import org.zafritech.core.data.initializr.DocumentInit;
import org.zafritech.core.data.initializr.DocumentTemplatesInit;
import org.zafritech.core.data.initializr.EntityTypesInit;
import org.zafritech.core.data.initializr.FolderInit;
import org.zafritech.core.data.initializr.InfoClassesInit;
import org.zafritech.applications.docman.data.initializr.DocmanInit;
import org.zafritech.applications.risksman.data.initializr.RiskmanInit;
import org.zafritech.core.data.initializr.LocalesDataInit;
import org.zafritech.core.data.initializr.ProjectCompanyRolesInit;
import org.zafritech.core.data.initializr.ProjectsInit;
import org.zafritech.core.data.initializr.SIUnitsDataInit;
import org.zafritech.core.data.initializr.SnaphotsInit;
import org.zafritech.core.data.initializr.TemplateVariablesInit;
import org.zafritech.core.data.initializr.UserRolesInit;
import org.zafritech.core.data.initializr.UsersInit;

/**
 *
 * @author LukeS
 */
@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private RunOnceTaskRepository runOnceTaskRepository;

    @Autowired
    private TemplateVariablesInit templateVariablesInit;

    @Autowired
    private UserRolesInit userRolesInit;
   
    @Autowired
    private UsersInit userInit;
  
    @Autowired
    private EntityTypesInit entityTypesInit;
  
    @Autowired
    private ClaimTypesInit claimTypesInit;
    
    @Autowired
    private LocalesDataInit localesDataInit;
    
    @Autowired
    private SIUnitsDataInit siUnitsDataInit;
    
    @Autowired
    private DefinitionsInit definitionsDataInit;
    
    @Autowired
    private CountriesDataInit countriesDataInit;
    
    @Autowired
    private InfoClassesInit infoClassesInit;
    
    @Autowired
    private CompaniesInit companiesInit;

    @Autowired
    private ProjectsInit projectsInit;

    @Autowired
    private ProjectCompanyRolesInit projectCompanyRolesInit;

    @Autowired
    private DocumentTemplatesInit documentTemplatesInit;
    
    @Autowired
    private FolderInit folderInit;
    
    @Autowired
    private DocumentInit documentInit;
    
    @Autowired
    private SnaphotsInit snaphotsInit;
    
    @Autowired
    private ContentDescriptorsInit contentDescriptorsInit;
    
//    @Autowired
//    private DocmanInit libraryInit;

    @Autowired
    private RequirementsInit requirementsInit;

    @Autowired
    private IntegrationInit integrationInit;

    @Autowired
    private RiskmanInit riskmanInit;

    @Autowired
    private DocmanInit docmanInit;
    
    @Autowired
    private InterfaceTypesInit interfaceTypesInit;
    
    @Autowired
    private ElementInit integrationElementsInit;
    
    @Autowired
    private InterfaceInit InterfacesInit;
    
    @Override
    public RunOnceTask initEntityTypes() {
        
        if (!isInitComplete("ENTITY_TYPES_INIT")) {
            
            entityTypesInit.init();
            return completeTask("ENTITY_TYPES_INIT");
        }
        
        return null;
    }
   
    @Transactional
    @Override
    public RunOnceTask initTemplateVariables() {
        
        if (!isInitComplete("TEMPLATE_VARIABLES_INIT")) {
            
            templateVariablesInit.init();
            return completeTask("TEMPLATE_VARIABLES_INIT");
        }
        
        return null;
    }
    
    @Transactional
    @Override
    public RunOnceTask initRoles() {
        
        if (!isInitComplete("ROLES_INIT")) {
        
            userRolesInit.init();
            return completeTask("ROLES_INIT");
        }
        
        return null;
    }
 
    @Transactional
    @Override
    public RunOnceTask initUsers() {

        if (!isInitComplete("USERS_INIT")) {

            userInit.init();
            return completeTask("USERS_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initClaimTypes() {

        if (!isInitComplete("CLAIM_TYPES_INIT")) {
            
            claimTypesInit.init();
            return completeTask("CLAIM_TYPES_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initLocales() {
        
        if (!isInitComplete("LOCALES_INIT")) {

            localesDataInit.init();
            return completeTask("LOCALES_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initSIUnits() {
        
        if (!isInitComplete("SI_UNITS_INIT")) {

            siUnitsDataInit.init();
            return completeTask("SI_UNITS_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initDefinitions() {
         
        if (!isInitComplete("DEFINITIONS_INIT")) {

            definitionsDataInit.init();
            return completeTask("DEFINITIONS_INIT");
        }
        
        
         return null;
    }
    
    @Override
    public RunOnceTask initCountries() {
        
        if (!isInitComplete("COUNTRIES_INIT")) {

            countriesDataInit.init();
            return completeTask("COUNTRIES_INIT");
        }
        
        return null;
    }
  
    @Override
    public RunOnceTask initInfoClasses() {
        
        if (!isInitComplete("INFO_CLASSES_INIT")) {

            infoClassesInit.init();
            return completeTask("INFO_CLASSES_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initCompanies() {
        
        if (!isInitComplete("COMPANIES_INIT")) {
            
            companiesInit.init();
            return completeTask("COMPANIES_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initProjects() {
       
        if (!isInitComplete("PROJECTS_INIT")) {
            
            projectsInit.init();
            return completeTask("PROJECTS_INIT");
        }
        
        return null;
    }
 
    @Override
    public RunOnceTask initProjectCompanyRoles() {
        
        if (!isInitComplete("PROJECT_COMPANY_ROLES_INIT")) {
        
            projectCompanyRolesInit.init();
            return completeTask("PROJECT_COMPANY_ROLES_INIT");
        }
        
        return null;
    }
    
    @Override
    public  RunOnceTask initDocumentTemplates() {
         
        if (!isInitComplete("DOCUMENT_TEMPLATES_INIT")) {
        
            documentTemplatesInit.init();
            return completeTask("DOCUMENT_TEMPLATES_INIT");
        }
        
        return null;
    }
    
    @Override
    public RunOnceTask initFolders() {
        
        if (!isInitComplete("FOLDERS_INIT")) {
        
            folderInit.init();
            return completeTask("FOLDERS_INIT");
        }
        
        return null;
    }
 
    @Override
    public RunOnceTask initDocumentContentDescriptors() {
        
        if (!isInitComplete("DOCUMENT_CONTENT_DESCRIPTORS_INIT")) {

            contentDescriptorsInit.init();
            return completeTask("DOCUMENT_CONTENT_DESCRIPTORS_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initDocuments() {
        
        if (!isInitComplete("DOCUMENTS_INIT")) {

            documentInit.init();
            return completeTask("DOCUMENTS_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initSnaphots() {
        
        if (!isInitComplete("SNAPSHOTS_INIT")) {
            
            snaphotsInit.init();
            return completeTask("SNAPSHOTS_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initApplications() {
        
        if (!isInitComplete("APPLICATIONS_INIT")) {
            
            integrationInit.init();
            requirementsInit.init();
            riskmanInit.init();
            docmanInit.init();
            
            return completeTask("APPLICATIONS_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initInterfaceTypes() {
        
        if (!isInitComplete("INTERFACE_TYPES_INIT")) {
            
            interfaceTypesInit.init();
            return completeTask("INTERFACE_TYPES_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initIntegrationElements() {
        
        if (!isInitComplete("INTEGRATION_ELEMENTS_INIT")) {
            
            integrationElementsInit.init();
            return completeTask("INTEGRATION_ELEMENTS_INIT");
        }
        
        return null;
    }

    @Override
    public RunOnceTask initIntegrationInterfaces() {
        
        if (!isInitComplete("INTEGRATION_INTERFACES_INIT")) {
            
            InterfacesInit.init();
            return completeTask("INTEGRATION_INTERFACES_INIT");
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
