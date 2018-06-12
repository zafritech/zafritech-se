/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectCompanyRole;

/**
 *
 * @author Luke Sibisi
 */
public interface ProjectCompanyRoleRepository extends CrudRepository<ProjectCompanyRole, Long> {
    
    List<ProjectCompanyRole> findByProjectOrderByProjectProjectNameAsc(Project project);
    
    ProjectCompanyRole findFirstByCompanyCompanyCode(String code);
}
