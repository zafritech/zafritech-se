/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.requirements.data.domain.Template;
import org.zafritech.applications.requirements.data.domain.TemplateItem;

/**
 *
 * @author LukeS
 */
public interface TemplateItemRepository extends CrudRepository<TemplateItem, Long> {
    
    TemplateItem findFirstByTemplateOrderBySystemIdDesc(Template template);
    
    List<TemplateItem> findByTemplateOrderBySystemIdAsc(Template template);
    
    List<TemplateItem> findByTemplateOrderBySystemIdDesc(Template template);
    
    List<TemplateItem> findByTemplateAndItemLevelOrderBySortIndexAsc(Template template, Integer level);
    
    List<TemplateItem> findByTemplateAndParentSysIdOrderBySortIndexAsc(Template template, String systemId);
}
