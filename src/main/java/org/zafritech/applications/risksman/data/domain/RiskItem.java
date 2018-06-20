package org.zafritech.applications.risksman.data.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.applications.risksman.enums.RiskClass;
import org.zafritech.applications.risksman.enums.RiskItemStatus;

@Entity(name = "RISKSMAN_RISKITEMS")
public class RiskItem implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    @Column(nullable = false)
    private String riskTitle;

    @Column(columnDefinition = "TEXT")
    private String riskDescription;
    
    private Integer riskProbability;
    
    private Integer riskSeverity;
    
    @Enumerated(EnumType.STRING)
    private RiskClass riskClass;
    
    @Enumerated(EnumType.STRING)
    private RiskItemStatus riskStatus;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public RiskItem() {
        
    }

    public RiskItem(String riskTitle, String riskDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.riskTitle = riskTitle;
        this.riskDescription = riskDescription;
        this.riskStatus = RiskItemStatus.CREATED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public RiskItem(String riskTitle, 
                    String riskDescription, 
                    Integer riskProbability, 
                    Integer riskSeverity) {
        
        this.uuId = UUID.randomUUID().toString();
        this.riskTitle = riskTitle;
        this.riskDescription = riskDescription;
        this.riskProbability = riskProbability;
        this.riskSeverity = riskSeverity;
        this.riskStatus = RiskItemStatus.CREATED;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
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

    public String getRiskTitle() {
        return riskTitle;
    }

    public void setRiskTitle(String riskTitle) {
        this.riskTitle = riskTitle;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public Integer getRiskProbability() {
        return riskProbability;
    }

    public void setRiskProbability(Integer riskProbability) {
        this.riskProbability = riskProbability;
    }

    public Integer getRiskSeverity() {
        return riskSeverity;
    }

    public void setRiskSeverity(Integer riskSeverity) {
        this.riskSeverity = riskSeverity;
    }

    public RiskClass getRiskClass() {
        return riskClass;
    }

    public void setRiskClass(RiskClass riskClass) {
        this.riskClass = riskClass;
    }

    public RiskItemStatus getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(RiskItemStatus riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

