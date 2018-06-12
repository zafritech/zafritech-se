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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_REFERENCES")
public class Reference implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String number;
    
    private String title;
    
    private String issue;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;

    public Reference() {
        
    }

    public Reference(String number, String title, String issue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.number = number;
        this.title = title;
        this.issue = issue;
        this.issueDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Reference{" + "id=" + getId() + ", uuId=" + getUuId() + ", "
                + "number=" + getNumber() + ", title=" + getTitle() + ", issue=" 
                + getIssue() + ", issueDate=" + getIssueDate() + '}';
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
