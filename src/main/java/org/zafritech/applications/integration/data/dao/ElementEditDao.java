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
public class ElementEditDao {
    
    private Long Id;
    
    private String sbs;
    
    private String name;
    
    private Long parentId;
    
    private Long entityId;
    
    private String elementDescription;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
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

    @Override
    public String toString() {
        
        return "ElementEditDao{" + "Id=" + Id + ", sbs=" + sbs 
                + ", name=" + name + ", parentId=" + parentId 
                + ", entityId=" + entityId + ", elementDescription=" 
                + elementDescription + '}';
    }
}
