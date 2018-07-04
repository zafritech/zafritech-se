/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.dao;

/**
 *
 * @author LukeS
 */
public class VerificationViewDao {
  
   
    private Long id;

    private String uuId;
    
    private String systemId;
    
    private String project;
    
    private String title;
    
    private String description;
    
    private String method;
    
    private String reference;
    
    private Integer interfaceCount;
    
    private String status;

    public VerificationViewDao() {
        
    }

    public VerificationViewDao(Long id, 
                               String uuId, 
                               String systemId, 
                               String project, 
                               String title, 
                               String description, 
                               String method, 
                               String reference, 
                               Integer interfaceCount, 
                               String status) {
        
        this.id = id;
        this.uuId = uuId;
        this.systemId = systemId;
        this.project = project;
        this.title = title;
        this.description = description;
        this.method = method;
        this.reference = reference;
        this.interfaceCount = interfaceCount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Integer interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        
        return "VerificationViewDao{" + "id=" + id + ", uuId=" + uuId + ", systemId=" + systemId + 
                                    ", project=" + project + ", title=" + title + ", description=" + description + 
                                    ", method=" + method + ", reference=" + reference + ", interfaceCount=" + interfaceCount + 
                                    ", status=" + status + '}';
    }
}
