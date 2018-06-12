/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author Luke Sibisi
 */
@Entity(name = "INTEGRATION_SYSTEM_ISSUES")
public class IntegrationSystemIssue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uuId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    private String systemIssueTitle;
    
    @Column(columnDefinition = "TEXT")
    private String systemIssueDescription;
    
    @Enumerated(EnumType.STRING)
    private InterfaceStatus systemIssue;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public IntegrationSystemIssue() {
        
    }

    public IntegrationSystemIssue(Project project, String systemIssueTitle) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.systemIssueTitle = systemIssueTitle;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public IntegrationSystemIssue(Project project, String systemIssueTitle, String systemIssueDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.systemIssueTitle = systemIssueTitle;
        this.systemIssueDescription = systemIssueDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public IntegrationSystemIssue(Project project, String systemIssueTitle, String systemIssueDescription, InterfaceStatus systemIssue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.systemIssueTitle = systemIssueTitle;
        this.systemIssueDescription = systemIssueDescription;
        this.systemIssue = systemIssue;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
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

    public String getSystemIssueTitle() {
        return systemIssueTitle;
    }

    public void setSystemIssueTitle(String systemIssueTitle) {
        this.systemIssueTitle = systemIssueTitle;
    }

    public String getSystemIssueDescription() {
        return systemIssueDescription;
    }

    public void setSystemIssueDescription(String systemIssueDescription) {
        this.systemIssueDescription = systemIssueDescription;
    }

    public InterfaceStatus getSystemIssue() {
        return systemIssue;
    }

    public void setSystemIssue(InterfaceStatus systemIssue) {
        this.systemIssue = systemIssue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        
        return "IntegrationSystemIssue{" + "id=" + id + ", uuId=" + uuId + 
                ", project=" + project + ", systemIssueTitle=" + systemIssueTitle + 
                ", systemIssueDescription=" + systemIssueDescription + 
                ", systemIssue=" + systemIssue + ", creationDate=" + creationDate + 
                ", modifiedDate=" + modifiedDate + '}';
    }
}
