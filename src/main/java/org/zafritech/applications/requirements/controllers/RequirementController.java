/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.projections.DocumentView;
import org.zafritech.core.data.repositories.ApplicationRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class RequirementController {

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserSessionService stateService;

    @Autowired
    private UserService userService;

    @RequestMapping("/app/requirements")
    public String getRequirementsHomePage(Model model) {

        if (hasNoValidateProject()) {
            
            return "redirect:/";
        }

        userSessionService.updateActiveApplication(applicationRepository.findFirstByApplicationName("requirements"));
        
        Project project = userSessionService.getLastOpenProject();
        List<DocumentView> documents = documentRepository.findDocumentViewByProject(project);

        model.addAttribute("project", project);
        model.addAttribute("documents", documents);

        return applicationService.getApplicationTemplateName() + "/views/requirements/index";
    }

    @RequestMapping("/app/requirements/document/{uuid}")
    public String getRequirementsDocument(@PathVariable(value = "uuid") String uuid, Model model) {

        Document document = documentRepository.findByUuId(uuid);

        // Update last access
        document.setLastAccessed(new Timestamp(System.currentTimeMillis()));
        documentRepository.save(document);

        stateService.updateRecentDocument(document);

        model.addAttribute("document", document);
        model.addAttribute("descriptor", document.getContentDescriptor().getDescriptorCode());

        return applicationService.getApplicationTemplateName() + "/views/requirements/document";
    }

    private boolean hasNoValidateProject() {

        Project openProject = userSessionService.getLastOpenProject();

        return openProject == null;
    }
}
