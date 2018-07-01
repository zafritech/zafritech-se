/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.domain.Application;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface UserSessionService {
   
    void updateOpenProject(Project project);
   
    void updateCloseProject(Project project);
   
    void updateActiveApplication(Application application);
   
    void updateOpenDocument(Document document);
   
    void updateCloseDocument(Document document);
    
    void updateRecentDocument(Document document);
   
    Project getLastOpenProject();
    
    Application getActiveApplication();
    
    List<Project> getUserProjects();
    
    List<Project> getOpenProjects();
    
    List<Document> getOpenDocuments();
    
    List<Document> getRecentDocuments();
    
    boolean isProjectOpen(Project project);
    
    Integer closeAllProjects();
}
