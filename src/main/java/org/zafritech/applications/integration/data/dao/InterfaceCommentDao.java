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
public class InterfaceCommentDao {
    
    private Long issueId;
    
    private String comment;
    
    private String action;
    
    private Long commentById;
    
    private String status;
    
    private Long actionById;

    public InterfaceCommentDao() {
        
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getCommentById() {
        return commentById;
    }

    public void setCommentById(Long commentById) {
        this.commentById = commentById;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActionById() {
        return actionById;
    }

    public void setActionById(Long actionById) {
        this.actionById = actionById;
    }

    @Override
    public String toString() {
        
        return "InterfaceCommentDao{" + "issueId=" + issueId 
                + ", comment=" + comment + ", action=" + action 
                + ", commentById=" + commentById + ", status=" + status 
                + ", actionById=" + actionById + '}';
    }
}
