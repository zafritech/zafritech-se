/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.applications.integration.data.dao.TreeElementDao;
import org.zafritech.applications.integration.data.domain.IntegrationEntity;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.repositories.IntegrationEntityRepository;
import org.zafritech.applications.integration.services.IntegrationService;
import org.zafritech.applications.integration.services.ReportsPDFService;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author lukes
 */
@Controller
public class IntegrationController {
	
    @Autowired
    private UserSessionService userSessionService;
	
    @Autowired
    private ApplicationService applicationService;
	
    @Autowired
    private IntegrationEntityRepository entityRepository;
    
    @Autowired
    private IntegrationService elementService;
    
    @Autowired
    private ReportsPDFService pdfReportService;

    @RequestMapping("/app/integration")
    public String IntegrationMainPage(Model model) {
        
        if (hasNoValidateProject()) { return "redirect:/"; }
        
        Project project = userSessionService.getLastOpenProject();
        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);
        
        String validtree = "";
        
        if (entities.size() > 0 ) {
        
            validtree = "sbsTreeValid";
        }
        
        model.addAttribute("project", project);
        model.addAttribute("validtree", validtree);
        
        return applicationService.getApplicationTemplateName() + "/views/integration/index";
    }
    
    @RequestMapping("/app/integration/elements")
    public String allTopLevelElements(Model model) {
     
        if (hasNoValidateProject()) { return "redirect:/"; }
         
        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);
        IntegrationEntity entity = entities.get(0);
        
        List<TreeElementDao> tree = elementService.getElementsTree(entity);
        
        model.addAttribute("tree", tree);
        model.addAttribute("entity", entity);
        model.addAttribute("entities", entities);
        
        return applicationService.getApplicationTemplateName() + "/views/integration/elements";
    }
    
    @RequestMapping("/app/integration/elements/{uuid}")
    public String TopLevelElementsByEntity(@PathVariable(value = "uuid") String uuid, Model model) {
    
        if (hasNoValidateProject()) { return "redirect:/"; }
             
        List<IntegrationEntity> entities = entityRepository.findByHasElements(true);
        IntegrationEntity entity = entityRepository.findByUuId(uuid);
                 
        List<TreeElementDao> tree = elementService.getElementsTree(entity);
   
        model.addAttribute("tree", tree);
        model.addAttribute("entity", entity);
        model.addAttribute("entities", entities);
              
        return applicationService.getApplicationTemplateName() + "/views/integration/elements";
    }
  
    @RequestMapping("/app/integration/system/download/pdf")
    public void downloadSystemPDFDocument(HttpServletResponse response) throws IOException, Exception {

        if (hasNoValidateProject()) { response.sendRedirect("/"); }
        
        Project project = userSessionService.getLastOpenProject();
        
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String fileName = timeFormat.format(System.currentTimeMillis()) + "_" + project.getProjectCode() + "_System Integration Report.pdf";

        byte[] baos = pdfReportService.getSystemPDFStatusReport(project); 

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream");
        response.setContentLength(baos.length);

        try (ServletOutputStream fout = response.getOutputStream()) {
            
            fout.write(baos);
            fout.flush();
        }
    }
  
    private boolean hasNoValidateProject() {
          
        Project openProject = userSessionService.getLastOpenProject();
	
        return openProject == null;
    }
}
