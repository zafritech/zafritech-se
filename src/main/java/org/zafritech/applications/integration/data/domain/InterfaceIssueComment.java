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
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.zafritech.applications.integration.enums.InterfaceStatus;
import org.zafritech.core.data.domain.ProjectCompanyRole;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_INTERFACE_ISSUE_COMMENTS")
public class InterfaceIssueComment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "issueId")
    @JsonBackReference
    private InterfaceIssue interfaceIssue;

    @Field(store = Store.NO)
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "commentById")
    private ProjectCompanyRole commentBy;
    
    @Enumerated(EnumType.STRING)
    private InterfaceStatus status;
    
    @Column(columnDefinition = "TEXT")
    private String commentAction;
    
    private String actionBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date targetDate;

    public InterfaceIssueComment() {
        
    }

    public InterfaceIssueComment(InterfaceIssue interfaceIssue, String comment) {
        
        this.uuId = UUID.randomUUID().toString();
        this.interfaceIssue = interfaceIssue;
        this.comment = comment;
        this.status = InterfaceStatus.INTERFACE_STATUS_OPEN;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.targetDate = new Timestamp(System.currentTimeMillis() + 14 * 24 * 60 * 1000);
    }

    public InterfaceIssueComment(InterfaceIssue interfaceIssue, String comment, InterfaceStatus status) {
        
        this.uuId = UUID.randomUUID().toString();
        this.interfaceIssue = interfaceIssue;
        this.comment = comment;
        this.status = status;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.targetDate = new Timestamp(System.currentTimeMillis() + 14 * 24 * 60 * 1000);
    }

    public InterfaceIssueComment(InterfaceIssue interfaceIssue, String comment, ProjectCompanyRole commentBy) {
        
        this.uuId = UUID.randomUUID().toString();
        this.interfaceIssue = interfaceIssue;
        this.comment = comment;
        this.commentBy = commentBy;
        this.status = InterfaceStatus.INTERFACE_STATUS_OPEN;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.targetDate = new Timestamp(System.currentTimeMillis() + 14 * 24 * 60 * 1000);
    }

    public InterfaceIssueComment(InterfaceIssue interfaceIssue, String comment, ProjectCompanyRole commentBy, InterfaceStatus status) {
        
        this.uuId = UUID.randomUUID().toString();
        this.interfaceIssue = interfaceIssue;
        this.comment = comment;
        this.commentBy = commentBy;
        this.status = status;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.targetDate = new Timestamp(System.currentTimeMillis() + 14 * 24 * 60 * 1000);
    }

    
    public InterfaceIssueComment(InterfaceIssue interfaceIssue, String comment, ProjectCompanyRole commentBy, String commentAction, String actionBy) {
        
        this.uuId = UUID.randomUUID().toString();
        this.interfaceIssue = interfaceIssue;
        this.comment = comment;
        this.commentBy = commentBy;
        this.commentAction = commentAction;
        this.actionBy = actionBy;
        this.status = InterfaceStatus.INTERFACE_STATUS_OPEN;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.targetDate = new Timestamp(System.currentTimeMillis() + 14 * 24 * 60 * 1000);
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

    public InterfaceIssue getInterfaceIssue() {
        return interfaceIssue;
    }

    public void setInterfaceIssue(InterfaceIssue interfaceIssue) {
        this.interfaceIssue = interfaceIssue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ProjectCompanyRole getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(ProjectCompanyRole commentBy) {
        this.commentBy = commentBy;
    }

    public InterfaceStatus getStatus() {
        return status;
    }

    public void setStatus(InterfaceStatus status) {
        this.status = status;
    }

    public String getCommentAction() {
        return commentAction;
    }

    public void setCommentAction(String commentAction) {
        this.commentAction = commentAction;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

}
