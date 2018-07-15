/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_ELEMENTS")
public class Element implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String sbs;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "entityId")
    private IntegrationEntity entity;
    
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Element parent;
    
    private String description;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_ELEMENT_VERIFICATION_ITEMS",
               joinColumns = {@JoinColumn(name = "element_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "verification_item_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<IntegrationVerification> verificationItems;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_ELEMENT_SYSTEM_ISSUES",
               joinColumns = {@JoinColumn(name = "element_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "system_issue_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<IntegrationSystemIssue> systemIssues;
    
    private Integer sortOrder;

    public Element() {
        
    }

    public Element(Project project,
                   String sbs,
                   String name, 
                   IntegrationEntity entity, 
                   Element parent, 
                   String description) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.sbs = sbs;
        this.name = name;
        this.entity = entity;
        this.parent = parent;
        this.description = description;
        this.sortOrder = sbs != null && !sbs.isEmpty() && sbs.indexOf('.') >= 0 ? Integer.valueOf(sbs.substring(sbs.lastIndexOf('.') + 1)) : Integer.valueOf(sbs);  
    }

    public Long getId() {
        return id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSbs() {
        return sbs;
    }

    public void setSbs(String sbs) {
        this.sbs = sbs;
        this.sortOrder = sbs != null && !sbs.isEmpty() && sbs.indexOf('.') >= 0 ? Integer.valueOf(sbs.substring(sbs.lastIndexOf('.') + 1)) : Integer.valueOf(sbs);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IntegrationEntity getEntity() {
        return entity;
    }

    public void setEntity(IntegrationEntity entity) {
        this.entity = entity;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<IntegrationVerification> getVerificationItems() {
        return verificationItems;
    }

    public void setVerificationItems(Set<IntegrationVerification> verificationItems) {
        this.verificationItems = verificationItems;
    }

    public Set<IntegrationSystemIssue> getSystemIssues() {
        return systemIssues;
    }

    public void setSystemIssues(Set<IntegrationSystemIssue> systemIssues) {
        this.systemIssues = systemIssues;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        
        return "Element{" + "id=" + id + ", uuId=" + uuId + ", sbs=" + sbs + 
                ", name=" + name + ", project=" + project + ", entity=" + entity + 
                ", parent=" + parent + ", description=" + description + 
                ", sortOrder=" + sortOrder + '}';
    }
}
