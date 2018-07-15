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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.DefinitionDomain;
import org.zafritech.core.enums.DefinitionScope;
import org.zafritech.core.enums.DefinitionTypes;

@Entity(name = "CORE_DEFINITIONS")
public class Definition implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String term;

    @Column(columnDefinition = "TEXT")
    private String termDefinition;
    
    @Enumerated(EnumType.STRING)
    private DefinitionTypes definitionType;
    
    @Enumerated(EnumType.STRING)
    private DefinitionDomain applicationDomain;
    
    @ManyToOne
    @JoinColumn(name = "localeId")
    private Locale language;
    
    @Enumerated(EnumType.STRING)
    private DefinitionScope definitionScope;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Override
    public String toString() {
        
        return "Definition{" + "id=" + getId() + ", uuId=" + getUuId() + ", term=" + 
                getTerm() + ", termDefinition=" + getTermDefinition() + ", definitionType=" + 
                getDefinitionType() + ", applicationDomain=" + getApplicationDomain() + ", language=" + 
                getLanguage() + ", definitionScope=" + getDefinitionScope() + ", scopeEntityId=" +'}';
    }

    public Definition() {
        
    }

    public Definition(String term, String termDefinition, DefinitionTypes type) {
        
        this.uuId = UUID.randomUUID().toString();
        this.term = term;
        this.termDefinition = termDefinition;
        this.definitionType = type;
        this.applicationDomain = DefinitionDomain.DOMAIN_SYSTEMS;
        this.definitionScope = DefinitionScope.SCOPE_GLOBAL;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Definition(String term, String termDefinition, DefinitionTypes type, Locale language) {
        
        this.uuId = UUID.randomUUID().toString();
        this.term = term;
        this.termDefinition = termDefinition;
        this.definitionType = type;
        this.applicationDomain = DefinitionDomain.DOMAIN_SYSTEMS;
        this.language = language;
        this.definitionScope = DefinitionScope.SCOPE_GLOBAL;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermDefinition() {
        return termDefinition;
    }

    public void setTermDefinition(String termDefinition) {
        this.termDefinition = termDefinition;
    }

    public DefinitionTypes getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(DefinitionTypes definitionType) {
        this.definitionType = definitionType;
    }

    public DefinitionDomain getApplicationDomain() {
        return applicationDomain;
    }

    public void setApplicationDomain(DefinitionDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public DefinitionScope getDefinitionScope() {
        return definitionScope;
    }

    public void setDefinitionScope(DefinitionScope definitionScope) {
        this.definitionScope = definitionScope;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

