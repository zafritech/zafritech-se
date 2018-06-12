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
public class InterfaceDao {
 
    private String primaryElement;
    
    private String secondaryElement;
    
    private String primaryEntity;
    
    private String secondaryEntity;
    
    private int interfaceLevel;
    
    private int interfaceType;
    
    private String interfaceTitle;
    
    private String interfaceDescription;
    
    private String interfaceNotes;
    
    private boolean status;

    public InterfaceDao() {
        
    }

    @Override
    public String toString() {
        
        return "InterfaceDao{" + "primaryElement=" + getPrimaryElement() + ", secondaryElement=" + 
                getSecondaryElement() + ", primaryEntity=" + getPrimaryEntity() + ", secondaryEntity=" + 
                getSecondaryEntity() + ", interfaceLevel=" + getInterfaceLevel() + ", interfaceType=" + 
                getInterfaceType() + ", interfaceTitle=" + getInterfaceTitle() + ", interfaceDescription=" + 
                getInterfaceDescription() + ", interfaceNotes=" + getInterfaceNotes() + ", openStatus=" + isOpenStatus() + '}';
    }

    public String getPrimaryElement() {
        return primaryElement;
    }

    public void setPrimaryElement(String primaryElement) {
        this.primaryElement = primaryElement;
    }

    public String getSecondaryElement() {
        return secondaryElement;
    }

    public void setSecondaryElement(String secondaryElement) {
        this.secondaryElement = secondaryElement;
    }

    public String getPrimaryEntity() {
        return primaryEntity;
    }

    public void setPrimaryEntity(String primaryEntity) {
        this.primaryEntity = primaryEntity;
    }

    public String getSecondaryEntity() {
        return secondaryEntity;
    }

    public void setSecondaryEntity(String secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    public int getInterfaceLevel() {
        return interfaceLevel;
    }

    public void setInterfaceLevel(int interfaceLevel) {
        this.interfaceLevel = interfaceLevel;
    }

    public int getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(int interfaceType) {
        this.interfaceType = interfaceType;
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

    public boolean isOpenStatus() {
        return status;
    }

    public void setOpenStatus(boolean openStatus) {
        this.status = openStatus;
    }
}
