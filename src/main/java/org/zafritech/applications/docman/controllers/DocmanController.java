/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.docman.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zafritech.applications.docman.data.domain.DocmanItem;
import org.zafritech.core.services.ApplicationService;
import org.zafritech.applications.docman.data.repositories.DocmanItemRepository;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class DocmanController {

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DocmanItemRepository docmanItemRepository;

    @Autowired
    private UserSessionService userSessionService;
	
    @RequestMapping(value = {"/app/docman", "/app/docman/list"})
    public String LibraryReferenceItemsList(Model model) {

        if (hasNoValidateProject()) { return "redirect:/"; }
        
        Project project = userSessionService.getLastOpenProject();
        
        EntityType EntityType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY");
        Folder folder = folderRepository.findFirstByParentAndFolderType(null, EntityType); 
        
        List<Folder> folders = folderRepository.findByParentOrderByFolderNameAsc(folder);
        List<DocmanItem> docmanItems = docmanItemRepository.findByFolderOrderByItemTitleAsc(folder);
        
        model.addAttribute("project", project);
        model.addAttribute("folder", folder);
        model.addAttribute("folders", folders);
        model.addAttribute("titles", docmanItems);

        return applicationService.getApplicationTemplateName() + "/views/docman/index";
    }

    @RequestMapping(value = "/docman/items/open/{uuid}", method = RequestMethod.GET)
    public void openDocmanItem(HttpServletResponse response,
                               @PathVariable(value = "uuid") String uuid) throws IOException {

        DocmanItem docmanItem = docmanItemRepository.findByUuId(uuid);

        String referencePath = data_dir + docmanItem.getItemPath();
        File file = new File(referencePath);
        String fileExtension = FilenameUtils.getExtension(referencePath);

        response.addHeader("content-disposition", "attachment;filename=" + docmanItem.getIdentifier() + "." + fileExtension);
        response.setContentType("application/octet-stream");

        InputStream inputStream = new FileInputStream(file);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }
    
    private boolean hasNoValidateProject() {
          
        Project openProject = userSessionService.getLastOpenProject();
	
        return openProject == null;
    }
}
