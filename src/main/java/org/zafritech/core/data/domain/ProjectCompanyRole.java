/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.CompanyRole;

/**
 *
 * @author Luke Sibisi
 */
@Entity(name = "CORE_PROJECT_COMPANY_ROLES")
public class ProjectCompanyRole implements Serializable {

    @Id
    @GeneratedValue
    private Long Id;

    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    
    @Enumerated(EnumType.STRING)
    private CompanyRole companyRole;
    
    private String diplayCode;
    
    @Column(columnDefinition = "TEXT")
    private String companyRoleDescription;
    
    private boolean isActive;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ProjectCompanyRole() {
        
    }

    public ProjectCompanyRole(Project project, Company company, CompanyRole companyRole, String diplayCode) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.company = company;
        this.companyRole = companyRole;
        this.diplayCode = diplayCode;
        this.isActive = true;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return Id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CompanyRole getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(CompanyRole companyRole) {
        this.companyRole = companyRole;
    }

    public String getDiplayCode() {
        return diplayCode;
    }

    public void setDiplayCode(String diplayCode) {
        this.diplayCode = diplayCode;
    }

    public String getCompanyRoleDescription() {
        return companyRoleDescription;
    }

    public void setCompanyRoleDescription(String companyRoleDescription) {
        this.companyRoleDescription = companyRoleDescription;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        
        return "ProjectCompanyRole{" + "Id=" + Id + ", uuId=" + uuId 
                + ", project=" + project + ", company=" + company 
                + ", companyRole=" + companyRole + ", diplayCode=" + diplayCode 
                + ", companyRoleDescription=" + companyRoleDescription 
                + ", isActive=" + isActive + ", creationDate=" + creationDate + '}';
    }

}
