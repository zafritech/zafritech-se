/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services;

import org.zafritech.applications.requirements.data.dao.TemplateDao;
import org.zafritech.applications.requirements.data.domain.Template;

/**
 *
 * @author LukeS
 */
public interface TemplateItemService {
    
    Template createTemplateFromDocument(TemplateDao templateDao);
    
    void createFileFromTemplate(Template template, String templateFormat);
}
