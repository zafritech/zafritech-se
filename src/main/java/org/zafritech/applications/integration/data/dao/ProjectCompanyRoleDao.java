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
public class ProjectCompanyRoleDao {
    
    private String code;
    private String displayCode;
    private String name;
    private String shortName;
    private String entityRole;
    
    public ProjectCompanyRoleDao() {
        
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayCode() {
        return displayCode;
    }

    public void setDisplayCode(String displayCode) {
        this.displayCode = displayCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEntityRole() {
        return entityRole;
    }

    public void setEntityRole(String entityRole) {
        this.entityRole = entityRole;
    }

    @Override
    public String toString() {
        
        return "ProjectCompanyRoleDao{" + "code=" + code 
                + ", displayCode=" + displayCode + ", name=" + name 
                + ", shortName=" + shortName + ", entityRole=" + entityRole + '}';
    }
}
