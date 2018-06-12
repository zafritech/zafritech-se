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
public class ElementCreateDao {
    
    private String sbs;
    
    private String name;
    
    private Long parentId;
    
    private Long entityId;
    
    private String elementDescription;

    @Override
    public String toString() {
        
        return "ElementCreateDao{" + "sbs=" + getSbs() + ", name=" + 
                getName() + ", parentId=" + getParentId() + ", entityId=" + 
                getEntityId() + ", elementDescription=" + getElementDescription() + '}';
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getElementDescription() {
        return elementDescription;
    }

    public void setElementDescription(String elementDescription) {
        this.elementDescription = elementDescription;
    }
}
