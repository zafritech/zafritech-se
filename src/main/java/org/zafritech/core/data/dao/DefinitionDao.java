/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author Luke Sibisi
 */
public class DefinitionDao {
    
    private String term;
    
    private String definition;
    
    private String type;
    
    private String domain;
    
    private String locale;
 
    private String scope;

    @Override
    public String toString() {
        
        return "DefinitionDao{" + "term=" + getTerm() + ", definition=" + 
                getDefinition() + ", type=" + getType() + ", domain=" + 
                getDomain() + ", locale=" + getLocale() + ", scope=" + getScope() + '}';
    }

    public DefinitionDao() {
        
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
