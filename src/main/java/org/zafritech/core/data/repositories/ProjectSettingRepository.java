/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectSetting;
import org.zafritech.core.enums.ProjectSettingType;

/**
 *
 * @author LukeS
 */
public interface ProjectSettingRepository extends CrudRepository<ProjectSetting, Long> {
    
    ProjectSetting findFirstByProjectAndSettingType(Project project, ProjectSettingType type);
    
    List<ProjectSetting> findByProject(Project project);
}
