/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_INTERFACES")
public class Interface implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    private String systemId;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "primaryElementId")
    private Element primaryElement;
    
    @ManyToOne
    @JoinColumn(name = "secondaryElementId")
    private Element secondaryElement;
    
    @ManyToOne
    @JoinColumn(name = "primaryEntityId")
    private IntegrationEntity primaryEntity;
    
    @ManyToOne
    @JoinColumn(name = "secondaryEntityId")
    private IntegrationEntity secondaryEntity;
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_INTERFACE_INTERFACE_TYPES",
               joinColumns = {@JoinColumn(name = "interface_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "interface_type_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private List<InterfaceType> interfaceTypes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_INTERFACE_REFERENCES",
               joinColumns = {@JoinColumn(name = "interface_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "reference_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Reference> references = new HashSet<Reference>();
    
    private int interfaceLevel;
    
    private String interfaceTitle;
    
    @Column(columnDefinition = "TEXT")
    private String interfaceDescription;
    
    @Column(columnDefinition = "TEXT")
    private String interfaceNotes;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "interfaceId")
    @JsonManagedReference
    @OrderBy("creationDate DESC")
    private List<InterfaceIssue> issues = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "INTEGRATION_INTERFACE_VERIFICATION_ITEMS",
               joinColumns = {@JoinColumn(name = "interface_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "verification_item_id", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<IntegrationVerification> verificationItems;
    
    @Enumerated(EnumType.STRING)
    private InterfaceStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Interface() {
        
    }

    public Interface(String systemId,
                     Project project,
                     Element primaryElement, 
                     Element secondaryElement, 
                     IntegrationEntity primaryEntity, 
                     IntegrationEntity secondaryEntity, 
                     int interfaceLevel, 
                     String interfaceTitle, 
                     String interfaceDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.project = project;
        this.primaryElement = primaryElement;
        this.secondaryElement = secondaryElement;
        this.primaryEntity = primaryEntity;
        this.secondaryEntity = secondaryEntity;
        this.interfaceLevel = interfaceLevel;
        this.interfaceTitle = interfaceTitle;
        this.interfaceDescription = interfaceDescription;
        this.interfaceNotes = null;
        this.status = InterfaceStatus.INTERFACE_STATUS_OPEN;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Interface{" + "id=" + id + ", uuId=" + uuId + ", systemId=" + 
                systemId + ", primaryElement=" + primaryElement + ", secondaryElement=" + 
                secondaryElement + ", primaryEntity=" + primaryEntity + ", secondaryEntity=" + 
                secondaryEntity + ", interfaceTypes=" + interfaceTypes + ", references=" + 
                references + ", interfaceLevel=" + interfaceLevel + ", interfaceTitle=" + 
                interfaceTitle + ", interfaceDescription=" + interfaceDescription + ", interfaceNotes=" + 
                interfaceNotes + ", issues=" + issues + ", status=" + status + ", creationDate=" + 
                creationDate + ", modifiedDate=" + modifiedDate + '}';
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

    public Element getPrimaryElement() {
        return primaryElement;
    }

    public void setPrimaryElement(Element primaryElement) {
        this.primaryElement = primaryElement;
    }

    public Element getSecondaryElement() {
        return secondaryElement;
    }

    public void setSecondaryElement(Element secondaryElement) {
        this.secondaryElement = secondaryElement;
    }

    public IntegrationEntity getPrimaryEntity() {
        return primaryEntity;
    }

    public void setPrimaryEntity(IntegrationEntity primaryEntity) {
        this.primaryEntity = primaryEntity;
    }

    public IntegrationEntity getSecondaryEntity() {
        return secondaryEntity;
    }

    public void setSecondaryEntity(IntegrationEntity secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    public List<InterfaceType> getInterfaceTypes() {
        return interfaceTypes;
    }

    public void setInterfaceTypes(List<InterfaceType> interfaceTypes) {
        this.interfaceTypes = interfaceTypes;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public Set<IntegrationVerification> getVerificationItems() {
        return verificationItems;
    }

    public void setVerificationItems(Set<IntegrationVerification> verificationItems) {
        this.verificationItems = verificationItems;
    }

    public int getInterfaceLevel() {
        return interfaceLevel;
    }

    public void setInterfaceLevel(int interfaceLevel) {
        this.interfaceLevel = interfaceLevel;
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

    public List<InterfaceIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<InterfaceIssue> issues) {
        this.issues = issues;
    }

    public InterfaceStatus getStatus() {
        return status;
    }

    public void setStatus(InterfaceStatus status) {
        this.status = status;
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
}
