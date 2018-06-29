/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.risksman.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zafritech.applications.risksman.data.domain.RiskItem;
import org.zafritech.applications.risksman.data.repositories.RiskItemRepository;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class RiskmanController {
  
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private RiskItemRepository riskItemRepository;

    @Autowired
    private UserSessionService userSessionService;
	
    @RequestMapping(value = {"/app/riskman", "/app/riskman/list"})
    public String RisksItemsList(Model model) {

        if (hasNoValidateProject()) { return "redirect:/"; }
        
        Project project = userSessionService.getLastOpenProject();
        
        List<RiskItem> risks = new ArrayList<>();
        Iterable<RiskItem> source = riskItemRepository.findAll();
        source.forEach(risks::add);
        
        model.addAttribute("project", project);
        model.addAttribute("risks", risks);

        return applicationService.getApplicationTemplateName() + "/views/riskman/index";
    }

    @RequestMapping(value = "/riskman/items/open/{uuid}", method = RequestMethod.GET)
    public String openRiskItem(@PathVariable(value = "uuid") String uuid, Model model) throws IOException {
        
        RiskItem riskItem = riskItemRepository.findByUuId(uuid);
        
        model.addAttribute("risk", riskItem);
        
        return applicationService.getApplicationTemplateName() + "/views/riskman/risk";
    }
    
    private boolean hasNoValidateProject() {
          
        Project openProject = userSessionService.getLastOpenProject();
	
        return openProject == null;
    }
}
