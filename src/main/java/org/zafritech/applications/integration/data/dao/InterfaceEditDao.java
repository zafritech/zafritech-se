/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.dao;

/**
 *
 * @author lukes
 */
public class InterfaceEditDao {
    
    private Long Id;
    
    private Long primaryElementId;
    
    private Long secondaryElementId;
    
    private Long primaryEntityId;
    
    private Long secondaryEntityId;
    
    private Integer interfaceLevel;
    
    private String status;
    
    private String interfaceTitle;
    
    private String interfaceDescription;

    public InterfaceEditDao() {
        
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getPrimaryElementId() {
        return primaryElementId;
    }

    public void setPrimaryElementId(Long primaryElementId) {
        this.primaryElementId = primaryElementId;
    }

    public Long getSecondaryElementId() {
        return secondaryElementId;
    }

    public void setSecondaryElementId(Long secondaryElementId) {
        this.secondaryElementId = secondaryElementId;
    }

    public Long getPrimaryEntityId() {
        return primaryEntityId;
    }

    public void setPrimaryEntityId(Long primaryEntityId) {
        this.primaryEntityId = primaryEntityId;
    }

    public Long getSecondaryEntityId() {
        return secondaryEntityId;
    }

    public void setSecondaryEntityId(Long secondaryEntityId) {
        this.secondaryEntityId = secondaryEntityId;
    }

    public Integer getInterfaceLevel() {
        return interfaceLevel;
    }

    public void setInterfaceLevel(Integer interfaceLevel) {
        this.interfaceLevel = interfaceLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        
        return "InterfaceEditDao{" + "Id=" + Id + ", primaryElementId=" 
                + primaryElementId + ", secondaryElementId=" + secondaryElementId 
                + ", primaryEntityId=" + primaryEntityId + ", secondaryEntityId=" 
                + secondaryEntityId + ", interfaceLevel=" + interfaceLevel 
                + ", status=" + status + ", interfaceTitle=" + interfaceTitle 
                + ", interfaceDescription=" + interfaceDescription + '}';
    }
}
