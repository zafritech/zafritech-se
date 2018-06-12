/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.applications.requirements.data.dao.SearchDao;
import org.zafritech.applications.requirements.services.ItemSearchService;
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author LukeS
 */
@Controller
public class SearchController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ItemSearchService itemSearchService;

    @RequestMapping("/search")
    public String searchItems(String q, Integer size, Integer page, Model model) {

        SearchDao searchResults = null;

        try {

            searchResults = itemSearchService.search(q, size, page);

        } catch (Exception ex) {

        }

        model.addAttribute("results", searchResults);
        model.addAttribute("string", q.replace(" ", "+"));

        return applicationService.getApplicationTemplateName() + "/views/items/search";
    }
}
