package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_MESSAGE_ATTACHMENTS")
public class MessageAttachment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuid;
    
    private String attachmentName;
    
    private String attachmentPath;
    
    private String attachmentType;

    public MessageAttachment() {
        
    }

    public MessageAttachment(String attachmentName, String attachmentPath) {
        
        this.attachmentName = attachmentName;
        this.attachmentPath = attachmentPath;
    }

    public MessageAttachment(String attachmentName, String attachmentPath, String attachmentType) {
        
        this.attachmentName = attachmentName;
        this.attachmentPath = attachmentPath;
        this.attachmentType = attachmentType;
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}

