/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.docman.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.repositories.ApplicationRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;

/**
 *
 * @author LukeS
 */
@Component
public class DocmanInit {
   
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Transactional
    public void init() {
     
    	Application appDocman = new Application("docman", "Docment Management", "Docman");
        appDocman.setApplicationDescription("The Document management application enables the management of enginering document for system development.");
    	appDocman.setPublished(false);
    	appDocman.setFaIcon("fa-book");
        
        applicationRepository.save(appDocman);   
        
        Folder library = folderRepository.save(new Folder("Library", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), null, null, 0)); 
        
        folderRepository.save(new Folder("Books", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), library, null, 0));
        
        Folder standards = folderRepository.save(new Folder("Standards", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), library, null, 1));
        folderRepository.save(new Folder("Company", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 0));
        folderRepository.save(new Folder("EIA", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 1));
        folderRepository.save(new Folder("Euro Norm", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 2));
        folderRepository.save(new Folder("ISO/IEC/IEEE", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 3));
        folderRepository.save(new Folder("MIL_STD", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 4));
        folderRepository.save(new Folder("Other", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), standards, null, 5));
    }
}
