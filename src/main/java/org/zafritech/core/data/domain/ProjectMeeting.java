/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luke Sibisi
 */
@Entity(name = "CORE_PROJECT_MEETINGS")
public class ProjectMeeting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String uuId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @Column(columnDefinition = "TEXT")
    private String meetingName;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ProjectMeeting() {
        
    }

    public ProjectMeeting(Project project, String meetingName, Date creationDate) {
        this.project = project;
        this.meetingName = meetingName;
        this.creationDate = creationDate;
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

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        
        return "ProjectMeeting{" + "id=" + id + ", uuId=" + uuId + 
                ", project=" + project + ", meetingName=" + meetingName + 
                ", creationDate=" + creationDate + '}';
    }
}
