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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.Frequency;

/**
 *
 * @author Luke Sibisi
 */
@Entity(name = "CORE_PROJECT_MEETING_SERIES")
public class ProjectMeetingSeries implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uuId;

    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
    
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ProjectMeetingSeries() {
        
    }

    public ProjectMeetingSeries(String name, Project project) {
        
        this.uuId = UUID.randomUUID().toString();
        this.name = name;
        this.project = project;
        this.frequency = Frequency.ADHOC;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ProjectMeetingSeries(String name, Project project, Frequency frequency) {
        
        this.uuId = UUID.randomUUID().toString();
        this.name = name;
        this.project = project;
        this.frequency = frequency;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ProjectMeetingSeries(String name, String description, Project project, Frequency frequency) {
        
        this.uuId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.project = project;
        this.frequency = frequency;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        
        return "ProjectMeetingSeries{" + "id=" + id + ", uuId=" + uuId + ", name=" + name + 
               ", description=" + description + ", project=" + project + ", frequency=" + frequency + 
               ", creationDate=" + creationDate + '}';
    }
}
