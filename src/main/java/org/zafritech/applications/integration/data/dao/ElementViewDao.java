/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.dao;

/**
 *
 * @author Luke Sibisi
 */
public class ElementViewDao {
  
    private Long Id;
    
    private Long projectId;
    
    private Long entityId;
    
    private Long parentId;
    
    private String sbs;
    
    private String name;
    
    private String description;  

    private Integer interfaceCount;

    private Integer verificationItemCount;

    public ElementViewDao() {
        
    }

    public ElementViewDao(Long Id, 
                                   Long projectId, 
                                   Long entityId, 
                                   Long parentId, 
                                   String sbs, 
                                   String name, 
                                   String description) {
        
        this.Id = Id;
        this.projectId = projectId;
        this.entityId = entityId;
        this.parentId = parentId;
        this.sbs = sbs;
        this.name = name;
        this.description = description;
        this.interfaceCount = 0;
        this.verificationItemCount = 0;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSbs() {
        return sbs;
    }

    public void setSbs(String sbs) {
        this.sbs = sbs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Integer interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public Integer getVerificationItemCount() {
        return verificationItemCount;
    }

    public void setVerificationItemCount(Integer verificationItemCount) {
        this.verificationItemCount = verificationItemCount;
    }

    @Override
    public String toString() {
        
        return "ElementViewDao{" + "Id=" + Id + ", projectId=" + projectId + ", entityId=" + entityId + 
                               ", parentId=" + parentId + ", sbs=" + sbs + ", name=" + name + 
                               ", description=" + description + ", interfaceCount=" + interfaceCount + '}';
    }
}
