/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.applications.integration.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author lukes
 */
@Entity(name = "INTEGRATION_INTERFACE_TYPES")
public class InterfaceType implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String typeCode;
    
    private String typeName;

    public InterfaceType() {
        
    }

    public InterfaceType(String typeCode, String typeName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        
        return "InterfaceType{" + "id=" + getId() + ", uuId=" + getUuId() + ", "
                + "typeCode=" + getTypeCode() + ", typeName=" + getTypeName() + '}';
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
