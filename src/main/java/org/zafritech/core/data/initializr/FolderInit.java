/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.ProjectRepository;

/**
 *
 * @author LukeS
 */
@Component
public class FolderInit {
   
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
     
    @Transactional
    public void init() {
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
        Integer index = 0;
        
        for(Project project : projects) {
            
            Folder folder = folderRepository.save(new Folder(project.getProjectShortName(), entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"), null, project, index++)); 
                
            folderRepository.save(new Folder("Planning", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 0));
            folderRepository.save(new Folder("Specification", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 1));
            folderRepository.save(new Folder("Design", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 2));
            folderRepository.save(new Folder("Validation", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 3));
        }
    }
}
