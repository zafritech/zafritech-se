/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
@Entity(name = "INTEGRATION_VERIFICATIONS")
public class IntegrationVerification implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String systemId;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "methodId")
    private EntityType method;
    
    @ManyToOne
    @JoinColumn(name = "referenceId")
    private Reference reference;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_VERIFICATION_INTERFACES",
               joinColumns = {@JoinColumn(name = "verification_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "interface_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Interface> interfaces;
    
    @Enumerated(EnumType.STRING)
    private InterfaceStatus verificationStatus;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public IntegrationVerification() {
        
    }

    public IntegrationVerification(String systemId, Project project, String title) {
        
        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.project = project;
        this.title = title;
        this.verificationStatus = InterfaceStatus.INTERFACE_STATUS_OPEN;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityType getMethod() {
        return method;
    }

    public void setMethod(EntityType method) {
        this.method = method;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Set<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Set<Interface> interfaces) {
        this.interfaces = interfaces;
    }

    public InterfaceStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(InterfaceStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
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
        
        return "IntegrationVerification{" + "id=" + id + ", uuId=" + uuId + ", systemId=" + systemId + 
                                        ", project=" + project + ", title=" + title + ", description=" + description + 
                                        ", reference=" + reference + ", interfaces=" + interfaces + 
                                        ", verificationStatus=" + verificationStatus + ", creationDate=" + creationDate + 
                                        ", modifiedDate=" + modifiedDate + '}';
    }
}
