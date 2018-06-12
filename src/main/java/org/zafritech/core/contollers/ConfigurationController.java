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
import org.zafritech.core.services.ApplicationService;

/**
 *
 * @author LukeS
 */
@Controller
public class ConfigurationController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = {"/admin/configuration", "/admin/configuration/index"})
    public String configPage(Model model) {

        return applicationService.getApplicationTemplateName() + "/views/core/admin/config/index";
    }

}
