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
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
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
public class HomeController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserSessionService userSessionService;
	
    @Autowired
    private ApplicationService applicationService;
    
    @RequestMapping(value={"/", "/index"})
    public String homePage(Model model) {
        
        Project openProject = userSessionService.getLastOpenProject();
        
        if (openProject != null) {
            
            return "redirect:/projects/" + openProject.getUuId(); 
            
        } else {
        
            return "redirect:/projects/list";
        }
    }
    
    @RequestMapping("/docs/references/help")
    public String documentOnlineHelp(Model model) {
        
        return applicationService.getApplicationTemplateName() + "/views/docs/help";
    }
    
    @RequestMapping("/docs/references/links")
    public String documentReferenceLinks(Model model) {
        
        return applicationService.getApplicationTemplateName() + "/views/docs/links";
    }
}
