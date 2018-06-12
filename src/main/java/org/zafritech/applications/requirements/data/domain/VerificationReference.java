package org.zafritech.applications.requirements.data.domain;

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

import org.zafritech.applications.requirements.enums.ItemStatus;
import org.zafritech.core.data.domain.EntityType;

@Entity(name = "REQUIREMENTS_VV_REFERENCES")
public class VerificationReference implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "methodId")
    private EntityType method;
    
    @Column(columnDefinition = "TEXT")
    private String vvRef;
    
    @Column(columnDefinition = "TEXT")
    private String vvEvidence;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public VerificationReference() {
        
    }

    public VerificationReference(Item item, EntityType method) {
        
        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.method = method;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public VerificationReference(Item item, EntityType method, String reference) {
        
        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.method = method;
        this.vvRef = reference;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Verification and Validation Reference{" + "id=" + getId() + ", uuId=" 
                + getUuId() + ", item=" + getItem() + ", method=" + getMethod() 
                + ", vvRef=" + getVvRef() + ", vvEvidence=" 
                + getVvEvidence() + ", status=" + getStatus() + ", creationDate=" 
                + getCreationDate() + '}';
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public EntityType getMethod() {
        return method;
    }

    public void setMethod(EntityType method) {
        this.method = method;
    }

    public String getVvRef() {
        return vvRef;
    }

    public void setVvRef(String vvRef) {
        this.vvRef = vvRef;
    }

    public String getVvEvidence() {
        return vvEvidence;
    }

    public void setVvEvidence(String vvEvidence) {
        this.vvEvidence = vvEvidence;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

