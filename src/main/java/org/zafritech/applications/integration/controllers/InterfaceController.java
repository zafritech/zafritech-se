/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.applications.integration.data.domain.Element;
import org.zafritech.applications.integration.data.domain.Interface;
import org.zafritech.applications.integration.data.domain.InterfaceIssue;
import org.zafritech.applications.integration.data.repositories.ElementRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceIssueRepository;
import org.zafritech.applications.integration.data.repositories.InterfaceRepository;
import org.zafritech.applications.integration.services.InterfaceService;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserSessionService;
import org.zafritech.applications.integration.services.ReportsPDFService;

/**
 *
 * @author lukes
 */
@Controller
public class InterfaceController {

    @Autowired
    private UserSessionService userSessionService;
	
    @Autowired
    private ApplicationService applicationService;
	
    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private InterfaceIssueRepository issueRepository;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private ReportsPDFService pdfReportService;

    @RequestMapping("/integration/interfaces/{uuid}")
    public String InterfaceDetails(@PathVariable(value = "uuid") String uuid, Model model) {

        if (hasNoValidateProject()) { return "redirect:/"; }
        
        Interface intf = interfaceRepository.findByUuId(uuid);
        model.addAttribute("interface", intf);

        return applicationService.getApplicationTemplateName() + "/views/integration/interface";
    }

    @RequestMapping("/integration/interfaces/list/{uuid}")
    public String InterfacesList(@PathVariable(value = "uuid") String uuid, Model model) {
    
        if (hasNoValidateProject()) { return "redirect:/"; }
            
        Element element = elementRepository.findByUuId(uuid);
        List<Interface> interfaces = interfaceRepository.findByPrimaryElementOrSecondaryElement(element, element);
        
        model.addAttribute("detailsTitle", element.getName() + " - System Element Interfaces");
        model.addAttribute("elementId", element.getId());
        model.addAttribute("interfaces", interfaces);
        
        return applicationService.getApplicationTemplateName() + "/views/integration/interfaces-list";
    }
    
    @RequestMapping("/app/integration/interfaces/download")
    public void downloadAllInterfaces(HttpServletResponse response) throws IOException {
        
        if (hasNoValidateProject()) { response.sendRedirect("/"); }
        
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String fileName = timeFormat.format(System.currentTimeMillis()) + "_Interfaces.xlsx";

        XSSFWorkbook workbook = interfaceService.DownloadExcel();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        baos.close();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentLength(baos.size());

        ServletOutputStream fout = response.getOutputStream();
        fout.write(baos.toByteArray());
        fout.flush();
        fout.close();
    }
    
    @RequestMapping("/app/integration/interface/pdf/{id}")
    public void downloadInterfacePDFDocument(@PathVariable Long id, HttpServletResponse response) throws IOException, Exception {

        if (hasNoValidateProject()) { response.sendRedirect("/"); }
        
        Interface iface = interfaceRepository.findOne(id);

        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String fileName = timeFormat.format(System.currentTimeMillis()) + "_" + iface.getSystemId() + "_" + iface.getInterfaceTitle() + ".pdf";

        byte[] baos = pdfReportService.getInterfacePDFStatusReport(iface); 

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream");
        response.setContentLength(baos.length);

        try (ServletOutputStream fout = response.getOutputStream()) {
            
            fout.write(baos);
            fout.flush();
        }
    }
    
    @RequestMapping("/app/integration/interface/issue/pdf/{id}")
    public void downloadIssuePDFDocument(@PathVariable Long id, HttpServletResponse response) throws IOException, Exception {

        if (hasNoValidateProject()) { response.sendRedirect("/"); }
        
        InterfaceIssue issue = issueRepository.findOne(id);

        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String fileName = timeFormat.format(System.currentTimeMillis()) + "_" + issue.getSystemId() + "_Interface Issue Report.pdf";

        byte[] baos = pdfReportService.getIssuePDFStatusReport(issue); 

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
