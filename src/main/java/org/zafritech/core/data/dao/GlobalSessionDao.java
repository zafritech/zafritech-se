/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author Luke Sibisi
 */
public class GlobalSessionDao {
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String photoPath;
    
    private Integer projectsCount;
    
    private boolean hasOpenProject;
    
    private String projectUuId;

    public GlobalSessionDao() {
        
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(Integer projectsCount) {
        this.projectsCount = projectsCount;
    }

    public boolean isHasOpenProject() {
        return hasOpenProject;
    }

    public void setHasOpenProject(boolean hasOpenProject) {
        this.hasOpenProject = hasOpenProject;
    }

    public String getProjectUuId() {
        return projectUuId;
    }

    public void setProjectUuId(String projectUuId) {
        this.projectUuId = projectUuId;
    }

    @Override
    public String toString() {
        
        return "GlobalSessionDao{" + "firstName=" + firstName + ", lastName=" + 
                lastName + ", email=" + email + ", hasOpenProject=" + 
                hasOpenProject + ", projectUuId=" + projectUuId + '}';
    }
}
