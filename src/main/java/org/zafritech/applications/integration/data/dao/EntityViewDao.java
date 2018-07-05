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
public class EntityViewDao {
    
    private Long Id;

    private Long projectId;    

    private String companyCode;    

    private boolean hasElements;    
    
    private Integer interfaceCount;

    public EntityViewDao() {
        
    }

    public EntityViewDao(Long Id, Long projectId, String companyCode, boolean hasElements) {
        
        this.Id = Id;
        this.projectId = projectId;
        this.companyCode = companyCode;
        this.hasElements = hasElements;
        this.interfaceCount = 0;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public boolean isHasElements() {
        return hasElements;
    }

    public void setHasElements(boolean hasElements) {
        this.hasElements = hasElements;
    }

    public Integer getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Integer interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    @Override
    public String toString() {
        
        return "EntityViewDao{" + "Id=" + Id + ", projectId=" + projectId + ", companyCode=" + companyCode + 
                              ", hasElements=" + hasElements + ", interfaceCount=" + interfaceCount + '}';
    }

}
