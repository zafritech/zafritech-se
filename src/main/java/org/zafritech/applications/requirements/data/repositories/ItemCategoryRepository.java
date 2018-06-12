/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.data.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.applications.requirements.data.domain.ItemCategory;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, Long> {
    
    ItemCategory findByUuId(String uuid);
    
    List<ItemCategory> findByProjectOrderByCategoryNameAsc(Project project);
    
    List<ItemCategory> findByProjectOrderByCategoryNameAsc(Pageable pageable, Project project);
    
    ItemCategory findFirstByCategoryCode(String code);
    
    List<ItemCategory> findByLead(User user);
}
