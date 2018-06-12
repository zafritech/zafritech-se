package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity(name = "CORE_APPLICATIONS")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    @Column(unique = true, nullable = false)
    private String applicationName;

    @Column(unique = true, nullable = false)
    private String applicationTitle;

    @Column(columnDefinition = "TEXT")
    private String applicationDescription;

    private boolean published;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Application() {
        
    }

    public Application(String applicationName, String applicationTitle) {

        super();
        this.uuId = UUID.randomUUID().toString();
        this.applicationName = applicationName;
        this.applicationTitle = applicationTitle;
        this.published = true;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Application(String applicationName, String applicationTitle, String applicationDescription) {

        super();
        this.uuId = UUID.randomUUID().toString();
        this.applicationName = applicationName;
        this.applicationTitle = applicationTitle;
        this.applicationDescription = applicationDescription;
        this.published = true;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }
    
    @Override
    public String toString() {

        return "Application [id=" + id + ", uuId=" + uuId + ", applicationName=" + applicationName
                + ", applicationTitle=" + applicationTitle + ", applicationDescription=" + applicationDescription
                + ", published=" + published + ", createdDate=" + createdDate + "]";
    }
}
