/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_ENTITIES")
public class IntegrationEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String companyCode;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    
    private boolean hasElements;
    
    private String sbs;
    
    private Integer sortOrder;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public IntegrationEntity() {
        
    }

    public IntegrationEntity(Project project, Company company, String companyCode) {
    
        this.uuId = UUID.randomUUID().toString();    
        this.project = project;
        this.company = company;
        this.companyCode = companyCode;
        this.hasElements = true;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public IntegrationEntity(Project project, Company company, String companyCode, boolean hasElements) {
    
        this.uuId = UUID.randomUUID().toString();    
        this.project = project;
        this.company = company;
        this.companyCode = companyCode;
        this.hasElements = hasElements;
        this.creationDate = new Timestamp(System.currentTimeMillis());
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public String getSbs() {
        return sbs;
    }

    public void setSbs(String sbs) {
        this.sbs = sbs;
        this.sortOrder = sbs != null && !sbs.isEmpty() && sbs.indexOf('.') >= 0 ? Integer.valueOf(sbs.substring(sbs.lastIndexOf('.') + 1)) : Integer.valueOf(sbs);
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        
        return "IntegrationEntity{" + "id=" + id + ", uuId=" + uuId + 
                ", companyCode=" + companyCode + ", project=" + project + 
                ", company=" + company + ", hasElements=" + hasElements + 
                ", sbs=" + sbs + ", creationDate=" + creationDate + '}';
    }
}
