/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.applications.requirements.services.ItemPDFService;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class DocumentController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ItemPDFService itextService;

    @Autowired
    private UserSessionService stateService;

    @RequestMapping(value = {"/documents", "/documents/list"})
    public String documentsList(Model model) {

        model.addAttribute("documents", "documents");

        return applicationService.getApplicationTemplateName() + "/views/documents/list";
    }

    @RequestMapping("/documents/document/{uuid}")
    public String readMessage(@PathVariable(value = "uuid") String uuid,
            Model model) {

        Document document = documentRepository.findByUuId(uuid);

        // Update last access
        document.setLastAccessed(new Timestamp(System.currentTimeMillis()));
        documentRepository.save(document);

        stateService.updateRecentDocument(document);

        model.addAttribute("document", document);
        model.addAttribute("descriptor", document.getContentDescriptor().getDescriptorCode());

        return applicationService.getApplicationTemplateName() + "/views/documents/document";
    }

    @RequestMapping("/requirements/document/pdf/{uuid}")
    public void downloadPDFDocument(@PathVariable String uuid, HttpServletResponse response) throws IOException, Exception {

        Document document = documentRepository.findByUuId(uuid);

        String fileName = document.getIdentifier() + "-" + document.getDocumentIssue() + "_" + document.getDocumentType().getEntityTypeCode() + ".pdf";

        byte[] baos = itextService.getItemsPDFDocument(document);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream");
        response.setContentLength(baos.length);

        try (ServletOutputStream fout = response.getOutputStream()) {
            
            fout.write(baos);
            fout.flush();
        }
    }
}
