/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.requirements.services;

import java.util.List;

import org.zafritech.applications.requirements.data.domain.ItemCategory;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface StatisticsService {
    
    List<Object> fetchSnapshotData(Project project, String fromDate);
    
    List<Object> fetchSnapshotData(Project project, String fromDate, String toDate);
    
    List<Object> fetchStatusData(Project project);
    
    List<Object> fetchStatusData(Project project, ItemCategory category);
}
