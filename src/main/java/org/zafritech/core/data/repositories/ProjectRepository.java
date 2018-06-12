/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
    
    Project getByUuId(String uuid);
    
    List<Project> findAllByOrderByProjectName();
    
    Project findFirstByOrderByNumericNumberDesc();
    
    Project findFirstByProjectShortName(String name);
}
