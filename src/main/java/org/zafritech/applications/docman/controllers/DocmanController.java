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
    private DocmanItemRepository docmanItemRepository;

    @RequestMapping(value = {"/docman", "/docman/list"})
    public String LibraryReferenceItemsList(Model model) {

        model.addAttribute("titles", "titles");

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
}
