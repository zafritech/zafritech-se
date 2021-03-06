/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectCompanyRole;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.ProjectCompanyRoleRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class AdminProjectController {
  
    @Autowired
    private ApplicationService applicationService;
	
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private ProjectCompanyRoleRepository companyRoleRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private UserSessionService stateService;
    
    @RequestMapping(value = {"/admin/projects", "/admin/projects/list"})
    public String getProjectsList(Model model) {
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
       
        model.addAttribute("projects", projects);
         
        return applicationService.getApplicationTemplateName() + "/views/core/admin/project/index";
    }
    
    @RequestMapping("/admin/projects/{uuid}")
    public String getProject(@PathVariable String uuid, Model model) {
        
        Project project = projectRepository.getByUuId(uuid);
        
        if (project != null) {
        
            Folder folder = folderRepository.findFirstByProjectAndFolderType(project, entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"));
            List<User> members = claimService.findProjectMemberClaims(project);
            List<ProjectCompanyRole> companyRoles = companyRoleRepository.findByProjectOrderByProjectProjectNameAsc(project);
            
            model.addAttribute("project", project);
            model.addAttribute("companies", companyRoles);
            model.addAttribute("folder", folder);   
            model.addAttribute("members", members);

            stateService.closeAllProjects(); 
            stateService.updateOpenProject(project); 

            return applicationService.getApplicationTemplateName() + "/views/core/admin/project/project";
            
        } else {
            
            return applicationService.getApplicationTemplateName() + "/views/core/admin/project/index";
        } 
    }
}
