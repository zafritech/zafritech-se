/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.util.ArrayList;
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
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class ProjectController {

    @Autowired
    private ApplicationService applicationService;
	
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectCompanyRoleRepository companyRoleRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private UserSessionService stateService;
    
    @RequestMapping(value = {"/projects", "/projects/list"})
    public String getProjectsList(Model model) {
        
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> allProjects = projectRepository.findAllByOrderByProjectName();
        
        List<Project> projects = new ArrayList<>();
        
        allProjects.stream().filter((project) -> (isAdmin || claimService.isProjectMember(user, project))).forEachOrdered((project) -> {
            
            projects.add(project);
        });
        
        model.addAttribute("projects", projects);
        
        return applicationService.getApplicationTemplateName() + "/views/core/project/index";
    }
    
    @RequestMapping("/projects/{uuid}")
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

            stateService.updateOpenProject(project); 

            return applicationService.getApplicationTemplateName() + "/views/core/project/project";
        }
        
        return "redirect:/";
    }
    
    @RequestMapping("/projects/close/{uuid}")
    public String closeProject(@PathVariable String uuid, Model model) {
        
        Project project = projectRepository.getByUuId(uuid);
        
        if (project != null) {
        
            stateService.updateCloseProject(project);
        }
        
        return "redirect:/";
    }
}
