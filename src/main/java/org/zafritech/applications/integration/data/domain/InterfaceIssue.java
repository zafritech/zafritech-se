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
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zafritech.applications.integration.enums.InterfaceStatus;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_INTERFACE_ISSUES")
public class InterfaceIssue implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String systemId;
    
    private String uuId;

    @ManyToOne
    @JoinColumn(name = "interfaceId")
    @JsonBackReference
    private Interface issueInterface;
    
    private String issueTitle;
    
    @Column(columnDefinition = "TEXT")
    private String issueDescription;
    
    @Enumerated(EnumType.STRING)
    private InterfaceStatus status;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "issueId")
    @JsonManagedReference
    @OrderBy("creationDate DESC")
    private List<InterfaceIssueComment> comments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public InterfaceIssue() {
        
    }

    public InterfaceIssue(String systemId, 
                          Interface issueInterface, 
                          String issueTitle,
                          String issueDescription) {
        
        this.systemId = systemId;
        this.uuId = UUID.randomUUID().toString();
        this.issueInterface = issueInterface;
        this.issueTitle = issueTitle;
        this.issueDescription = issueDescription;
        this.status = InterfaceStatus.INTERFACE_STATUS_OPEN;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "InterfaceIssue{" + "id=" + id + ", systemId=" + 
                systemId + ", uuId=" + uuId + ", issueTitle=" + 
                issueTitle + ", issueDescription=" + issueDescription + ", status=" + 
                status + ", comments=" + comments + ", creationDate=" + 
                creationDate + ", modifiedDate=" + modifiedDate + '}';
    }

    public Long getId() {
        return id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Interface getIssueInterface() {
        return issueInterface;
    }

    public void setIssueInterface(Interface issueInterface) {
        this.issueInterface = issueInterface;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public InterfaceStatus getStatus() {
        return status;
    }

    public void setStatus(InterfaceStatus status) {
        this.status = status;
    }

    public List<InterfaceIssueComment> getComments() {
        return comments;
    }

    public void setComments(List<InterfaceIssueComment> comments) {
        this.comments = comments;
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
