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

    @Column(unique = true, nullable = false)
    private String applicationShortTitle;

    @Column(columnDefinition = "TEXT")
    private String applicationDescription;

    private String faIcon;

    private boolean published;
    
    private boolean docCentric;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    public Application() {
        
    }

    public Application(String applicationName, String applicationTitle, String applicationShortTitle) {

        super();
        this.uuId = UUID.randomUUID().toString();
        this.applicationName = applicationName;
        this.applicationTitle = applicationTitle;
        this.applicationShortTitle = applicationShortTitle;
        this.faIcon = "fa-window-maximize";
        this.published = true;
        this.docCentric = false;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Application(String applicationName, String applicationTitle, String applicationShortTitle, String applicationDescription) {

        super();
        this.uuId = UUID.randomUUID().toString();
        this.applicationName = applicationName;
        this.applicationTitle = applicationTitle;
        this.applicationShortTitle = applicationShortTitle;
        this.applicationDescription = applicationDescription;
        this.faIcon = "fa-window-maximize";
        this.published = true;
        this.docCentric = false;
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

    public String getApplicationShortTitle() {
        return applicationShortTitle;
    }

    public void setApplicationShortTitle(String applicationShortTitle) {
        this.applicationShortTitle = applicationShortTitle;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public void setFaIcon(String faIcon) {
        this.faIcon = faIcon;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isDocCentric() {
        return docCentric;
    }

    public void setDocCentric(boolean docCentric) {
        this.docCentric = docCentric;
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
