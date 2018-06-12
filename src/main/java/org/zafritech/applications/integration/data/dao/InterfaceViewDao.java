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
public class InterfaceViewDao {

    private Long Id;
    
    private String systemId;
    
    private Long projectId;
    
    private String projectName;
       
    private Long primaryEntityId;
    
    private String primaryEntityName;
    
    private String primaryEntityCode;
       
    private Long secondaryEntityId;
    
    private String secondaryEntityName;
    
    private String secondaryEntityCode;
    
    private Long primaryElementId;
    
    private String primaryElementSbs;
    
    private String primaryElementName;
       
    private Long secondaryElementId;
       
    private String secondaryElementSbs;
       
    private String secondaryElementName;
    
    private Integer interfaceLevel;
    
    private String interfaceTitle;
    
    private String interfaceDescription;
    
    private String interfaceNotes;
    
    private String status;    
    
    private Integer issues;

    public InterfaceViewDao() {
        
    }

    public InterfaceViewDao(Long Id, 
                            String systemId, 
                            Long projectId, 
                            String projectName, 
                            Long primaryEntityId, 
                            String primaryEntityName, 
                            String primaryEntityCode, 
                            Long secondaryEntityId, 
                            String secondaryEntityName, 
                            String secondaryEntityCode, 
                            Long primaryElementId, 
                            String primaryElementSbs, 
                            String primaryElementName, 
                            Long secondaryElementId, 
                            String secondaryElementSbs, 
                            String secondaryElementName, 
                            Integer interfaceLevel, 
                            String interfaceTitle, 
                            String interfaceDescription, 
                            String interfaceNotes, 
                            String status, 
                            Integer issues) {
        
        this.Id = Id;
        this.systemId = systemId;
        this.projectId = projectId;
        this.projectName = projectName;
        this.primaryEntityId = primaryEntityId;
        this.primaryEntityName = primaryEntityName;
        this.primaryEntityCode = primaryEntityCode;
        this.secondaryEntityId = secondaryEntityId;
        this.secondaryEntityName = secondaryEntityName;
        this.secondaryEntityCode = secondaryEntityCode;
        this.primaryElementId = primaryElementId;
        this.primaryElementSbs = primaryElementSbs;
        this.primaryElementName = primaryElementName;
        this.secondaryElementId = secondaryElementId;
        this.secondaryElementSbs = secondaryElementSbs;
        this.secondaryElementName = secondaryElementName;
        this.interfaceLevel = interfaceLevel;
        this.interfaceTitle = interfaceTitle;
        this.interfaceDescription = interfaceDescription;
        this.interfaceNotes = interfaceNotes;
        this.status = status;
        this.issues = issues;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getPrimaryEntityId() {
        return primaryEntityId;
    }

    public void setPrimaryEntityId(Long primaryEntityId) {
        this.primaryEntityId = primaryEntityId;
    }

    public String getPrimaryEntityName() {
        return primaryEntityName;
    }

    public void setPrimaryEntityName(String primaryEntityName) {
        this.primaryEntityName = primaryEntityName;
    }

    public String getPrimaryEntityCode() {
        return primaryEntityCode;
    }

    public void setPrimaryEntityCode(String primaryEntityCode) {
        this.primaryEntityCode = primaryEntityCode;
    }

    public Long getSecondaryEntityId() {
        return secondaryEntityId;
    }

    public void setSecondaryEntityId(Long secondaryEntityId) {
        this.secondaryEntityId = secondaryEntityId;
    }

    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

    public void setSecondaryEntityName(String secondaryEntityName) {
        this.secondaryEntityName = secondaryEntityName;
    }

    public String getSecondaryEntityCode() {
        return secondaryEntityCode;
    }

    public void setSecondaryEntityCode(String secondaryEntityCode) {
        this.secondaryEntityCode = secondaryEntityCode;
    }

    public Long getPrimaryElementId() {
        return primaryElementId;
    }

    public void setPrimaryElementId(Long primaryElementId) {
        this.primaryElementId = primaryElementId;
    }

    public String getPrimaryElementSbs() {
        return primaryElementSbs;
    }

    public void setPrimaryElementSbs(String primaryElementSbs) {
        this.primaryElementSbs = primaryElementSbs;
    }

    public String getPrimaryElementName() {
        return primaryElementName;
    }

    public void setPrimaryElementName(String primaryElementName) {
        this.primaryElementName = primaryElementName;
    }

    public Long getSecondaryElementId() {
        return secondaryElementId;
    }

    public void setSecondaryElementId(Long secondaryElementId) {
        this.secondaryElementId = secondaryElementId;
    }

    public String getSecondaryElementSbs() {
        return secondaryElementSbs;
    }

    public void setSecondaryElementSbs(String secondaryElementSbs) {
        this.secondaryElementSbs = secondaryElementSbs;
    }

    public String getSecondaryElementName() {
        return secondaryElementName;
    }

    public void setSecondaryElementName(String secondaryElementName) {
        this.secondaryElementName = secondaryElementName;
    }

    public Integer getInterfaceLevel() {
        return interfaceLevel;
    }

    public void setInterfaceLevel(Integer interfaceLevel) {
        this.interfaceLevel = interfaceLevel;
    }

    public String getInterfaceTitle() {
        return interfaceTitle;
    }

    public void setInterfaceTitle(String interfaceTitle) {
        this.interfaceTitle = interfaceTitle;
    }

    public String getInterfaceDescription() {
        return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription) {
        this.interfaceDescription = interfaceDescription;
    }

    public String getInterfaceNotes() {
        return interfaceNotes;
    }

    public void setInterfaceNotes(String interfaceNotes) {
        this.interfaceNotes = interfaceNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIssues() {
        return issues;
    }

    public void setIssues(Integer issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        
        return "InterfaceViewDao{" + "Id=" + Id + ", systemId=" + systemId + 
                ", projectId=" + projectId + ", projectName=" + projectName + 
                ", primaryEntityId=" + primaryEntityId + ", primaryEntityName=" + primaryEntityName + 
                ", primaryEntityCode=" + primaryEntityCode + ", secondaryEntityId=" + secondaryEntityId + 
                ", secondaryEntityName=" + secondaryEntityName + ", secondaryEntityCode=" + secondaryEntityCode + 
                ", primaryElementId=" + primaryElementId + ", primaryElementSbs=" + primaryElementSbs + 
                ", primaryElementName=" + primaryElementName + ", secondaryElementId=" + secondaryElementId + 
                ", secondaryElementSbs=" + secondaryElementSbs + ", secondaryElementName=" + secondaryElementName + 
                ", interfaceLevel=" + interfaceLevel + ", interfaceTitle=" + interfaceTitle + 
                ", interfaceDescription=" + interfaceDescription + ", interfaceNotes=" + interfaceNotes + 
                ", status=" + status + ", issues=" + issues + '}';
    }
}
