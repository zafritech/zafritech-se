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
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "seriesId")
    private ProjectMeetingSeries series;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    private String meetingVenue;
    
    private String minutesRefLink;

    public ProjectMeeting() {
        
    }

    public ProjectMeeting(Project project, String meetingName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.name = meetingName;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ProjectMeeting(Project project, String meetingName, ProjectMeetingSeries series) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.name = meetingName;
        this.series = series;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ProjectMeeting(Project project, String meetingName, ProjectMeetingSeries series, Date creationDate) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.name = meetingName;
        this.series = series;
        this.creationDate = creationDate;
    }

    public ProjectMeeting(Project project, String meetingName, ProjectMeetingSeries series, Date creationDate, String meetingVenue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.project = project;
        this.name = meetingName;
        this.series = series;
        this.creationDate = creationDate;
        this.meetingVenue = meetingVenue;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectMeetingSeries getSeries() {
        return series;
    }

    public void setSeries(ProjectMeetingSeries series) {
        this.series = series;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMeetingVenue() {
        return meetingVenue;
    }

    public void setMeetingVenue(String meetingVenue) {
        this.meetingVenue = meetingVenue;
    }

    public String getMinutesRefLink() {
        return minutesRefLink;
    }

    public void setMinutesRefLink(String minutesRefLink) {
        this.minutesRefLink = minutesRefLink;
    }

    @Override
    public String toString() {
        
        return "ProjectMeeting{" + "id=" + id + ", uuId=" + uuId + ", project=" + project + 
               ", meetingName=" + name + ", creationDate=" + creationDate + 
               ", meetingVenue=" + meetingVenue + ", minutesRefLink=" + minutesRefLink + '}';
    }

}
